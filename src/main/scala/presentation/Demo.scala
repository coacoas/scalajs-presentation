package presentation


import japgolly.scalajs.react.React
import monifu.concurrent.Implicits.globalScheduler
import monifu.concurrent.schedulers.AsyncScheduler
import monifu.reactive.observables.ConnectableObservable
import monifu.reactive.{Ack, Observable}
import org.scalajs.dom
import org.scalajs.dom.ext.Color
import org.scalajs.dom.raw.MouseEvent
import presentation.Components.base

import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport
import scala.util.Random
import presentation.extensions.reactive.ext._

case class Point(x: Double, y: Double)
case class Circle(center: Point, radius: Int, color: Color) {
  def colorString: String = f"#${color.r}%02x${color.g}%02x${color.b}%02x".toUpperCase

}

@JSExport
class Demo {

  def randomColor: Color = {
    val r = Random.nextInt(255)
    val g = Random.nextInt(255)
    val b = Random.nextInt(255)
    Color(r, g, b)
  }

  @JSExport
  def main(root: dom.Node): Unit = {
    val mousemove: ConnectableObservable[Point] = Observable.create[Point] { subscriber =>
      dom.document.onmousemove = (ev: MouseEvent) => {
        subscriber.onNext(Point(ev.pageX, ev.pageY))
      }
    }.publish
    mousemove.connect()

    val circleSeq = mousemove
      .map(p => Circle(p, 1, randomColor))
      .tumblingBuffer(33.millis)
      .scan(Vector[Circle]()) { (circles, newCircles) =>
      val bigger = circles.map(c => c.copy(radius = c.radius + 1))
        .filter(_.radius <= 30)

      bigger ++ newCircles
    }

    circleSeq.foreach { elements =>
      React.render(Components.circles(elements), root)
    }
  }
}
