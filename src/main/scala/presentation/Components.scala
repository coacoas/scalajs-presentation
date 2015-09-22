package presentation

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._

/**
 * Created by bcarlson on 9/22/15.
 */
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
    <.div(circles.map(circle.apply(_)))
  }).build

}
