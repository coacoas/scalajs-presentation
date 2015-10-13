package presentation.reactive

import monifu.reactive.Observable

import scala.concurrent.duration.FiniteDuration

object ext {
  private class BufferedObservable[U](emitOn: Observable[U]) {
    def buffer[T](obs: Observable[T]): Observable[Seq[T]] = {
      sealed trait Ops
      case class Store(value: T) extends Ops
      case class Hold(values: Seq[T]) extends Ops
      case object Release extends Ops
      case class Emit(values: Seq[T]) extends Ops

      val inbound = obs.map(e => Store(e))
      val emit = emitOn.map(_ => Release)
      val instructions: Observable[Ops] = Observable.merge(inbound, emit)

      instructions.scan(Hold(Vector[T]()): Ops) { (store, next) =>
        next match {
          case Store(t) => store match {
            case Hold(archive) => Hold(archive :+ t)
            case Emit(data) => Hold(Vector(t))
          }
          case Release => store match {
            case Hold(archive) => Emit(archive)
            case Emit(data) => Emit(Seq[T]())
          }
        }
      }.flatMap {
        case Emit(values) => Observable(values)
        case _ => Observable.empty
      }

    }
  }

  implicit class ObservableExt[T](val obs: Observable[T]) extends AnyVal {
    def tumblingBuffer(interval: FiniteDuration): Observable[Seq[T]] = {
      val buffered = new BufferedObservable[Long](Observable.interval(interval))

      obs.lift(buffered.buffer)
    }
  }
}
