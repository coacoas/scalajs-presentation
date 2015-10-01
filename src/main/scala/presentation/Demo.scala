package presentation

import org.scalajs
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{html, _}
import org.scalajs.dom.raw.{CanvasRenderingContext2D, KeyboardEvent}

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

@JSExport
class Demo {
  case class Point(x: Int, y: Int) {
    def +(p: Point) = Point(x + p.x, y + p.y)
    def %(p: Point) = Point(x % p.x, y % p.y)
    def near(distance: Int)(other: Point) =
      math.abs(other.x - x) <= distance &&
        math.abs(other.y - y) <= distance
  }

  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = 512
    canvas.height = 384
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def randomPosition = Point(Random.nextInt(canvas.width), Random.nextInt(canvas.height))

    val keys: mutable.Set[Int] = collection.mutable.Set[Int]()
    document.onkeypress = (ev: KeyboardEvent) => keys.add(ev.keyCode)
    document.onkeyup = (ev: KeyboardEvent) => keys.remove(ev.keyCode)

    val moves = Map(
      KeyCode.Down -> Point(0, 4),
      KeyCode.Up -> Point(0, -4),
      KeyCode.Left -> Point(-4, 0),
      KeyCode.Right -> Point(4, 0))

    def draw(style: scala.scalajs.js.Any)(p: Point) = {
      ctx.fillStyle = style
      ctx.fillRect(p.x, p.y, 5, 5)
    }

    def render(player: Point, enemies: Seq[Point], live: Boolean): Unit = {
        clear()

        enemies.foreach(draw("red"))
        draw(if (live) "white" else "gray")(player)

        if (live)
          scalajs.dom.setTimeout(() => render(
            keys.filter(moves.keySet).map(moves).foldLeft(player)(_ + _),
            enemies.map(enemy => (enemy + Point(0, 2)) % Point(canvas.width, canvas.height)) _,
            !enemies.exists(_.near(5)(player))), 33)
      }

    render(Point(canvas.width / 2, canvas.height / 2),
      Seq.fill(50)(randomPosition), live = true)
  }
}
