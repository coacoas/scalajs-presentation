package presentation.canvas

import org.scalajs
import org.scalajs.dom.raw.{CanvasRenderingContext2D, KeyboardEvent}
import org.scalajs.dom.{html, _}
import presentation.geometry.Point

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport
import scala.util.Random
import scalaz.syntax.id._

@JSExport
class Dodge {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    (canvas.width, canvas.height) = (512, 384)
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    def randomPosition = Point(Random.nextInt(canvas.width), Random.nextInt(canvas.height))

    type Move = Point => Point
    val moves = Map[Char, Move] (
      's' -> (p => Point(p.x, p.y + 4)),
      'w' -> (p => Point(p.x, p.y -4)),
      'a' -> (p => Point(p.x - 4, p.y)),
      'd' -> (p => Point(p.x + 4, p.y)))

    val keys: mutable.Set[Char] = collection.mutable.Set[Char]()
    document.onkeypress = (ev: KeyboardEvent) => keys.add(ev.keyCode.toChar.toLower)
    document.onkeyup = (ev: KeyboardEvent) => keys.remove(ev.keyCode.toChar.toLower)

    def draw(style: scala.scalajs.js.Any)(p: Point) = {
      ctx.fillStyle = style
      ctx.fillRect(p.x, p.y, 5, 5)
    }

    def render(player: Point, enemies: Seq[Point], live: Boolean): Unit = {
      clear()

      enemies.foreach(draw("red"))
      player |> draw(if (live) "white" else "gray")

      if (live) {
        val nextPlayer = keys.filter(moves.isDefinedAt)
          .map(moves)
          .foldLeft(player) { (position, move) =>
            move(position)
          }

        val nextEnemies = enemies.map(enemy => Point(enemy.x, (enemy.y + 2) % canvas.height))
        scalajs.dom.setTimeout(() => render(
          nextPlayer,
          nextEnemies,
          !enemies.exists(_.near(5)(player))), 33)
      }
    }

    render(Point(canvas.width / 2, canvas.height / 2),
      Seq.fill(50)(randomPosition), live = true)
  }
}
