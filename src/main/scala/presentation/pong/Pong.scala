package presentation.pong

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import org.scalajs.dom.ext.Color
import org.scalajs.dom.svg.Point

import scala.scalajs.js.annotation.JSExport

case class Velocity(dx: Int, dy: Int)
case class Paddle(location: Point, size: Int)
case class Ball(location: Point, size: Int, velocity: Velocity)

case class State(players: Vector[Paddle], ball: Ball)

@JSExport
class Pong {
  type Move = Paddle => Paddle

  val paddle = ReactComponentB[Paddle]("paddle").render { (paddle: Paddle) =>
    <.svg.svg(
      ^.cls := "player",
      <.svg.rect(
        ^.top := paddle.location.y,
        ^.left := paddle.location.x,
        ^.svg.color := "white",
        ^.svg.fill := "white"))
  }.build

  def pong(): Unit = {
  }
}

