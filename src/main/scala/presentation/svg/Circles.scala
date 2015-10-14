package presentation.svg

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{React, ReactComponentB}
import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive.Observable
import monifu.reactive.observables.ConnectableObservable
import org.scalajs.dom
import org.scalajs.dom.ext.Color
import org.scalajs.dom.raw.MouseEvent
import presentation.geometry.{Circle, Point}
import presentation.reactive.ext._

import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

@JSExport
class Circles {

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
        subscriber.onNext(Point(ev.clientX.toInt, ev.clientY.toInt))
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

    mousemove.zip(circleSeq).foreach { zipped =>
      React.render(Components.base(zipped), root)
    }
  }
}

object Components {
  val base = ReactComponentB[(Point, Seq[Circle])]("base").render { data =>
    val (pos, circles) = data
    <.div(
      position(pos),
      circles.map(circle(_)))
  }.build

  val position = ReactComponentB[Point]("point").render { point =>
    <.div(
      ^.color := "white",
      s"X: ${point.x}, Y: ${point.y}")
  }.build

  val circle = ReactComponentB[Circle]("circle").render { circle =>
    val wh = circle.radius * 2
    val margin = -wh
    val bgColor = f"#0000${circle.color.b}%02x"

    <.svg.svg(
      ^.cls := "svgpos",
      ^.opacity := 1,
      ^.width := wh,
      ^.height := wh,
      ^.top := (circle.center.y - circle.radius),
      ^.left := (circle.center.x - circle.radius),
      <.svg.circle(
        ^.svg.cx := circle.radius,
        ^.svg.cy := circle.radius,
        ^.svg.r := circle.radius,
        ^.svg.stroke := circle.colorString,
        ^.svg.strokeWidth := 2,
        ^.svg.color := circle.colorString,
        ^.svg.fill := "none"))
  }.build

  val circles = ReactComponentB[Seq[Circle]]("circle").render(circles => {
    <.div(circles.map(circle(_)))
  }).build

}
