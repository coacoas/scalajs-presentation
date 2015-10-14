package presentation.svg

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import org.scalajs.dom.ext.KeyCode

import scala.collection.mutable
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

@JSExport
class Dodge {
  case class Game(width: Int, height: Int, player: Player, enemies: List[Enemy])
  case class Player(x: Int, y: Int, alive: Boolean = true)
  case class Enemy(x: Int, y: Int)
  case class Move(dx: Int, dy: Int) {
    def +(that: Move) = Move(dx + that.dx, dy + that.dy)
  }

  @JSExport
  def main() = {
    val (w, h) = (512, 384)

    val availableMoves = Map[Int, Move](
      KeyCode.W -> Move(0, -4),
      KeyCode.S -> Move(0, 4),
      KeyCode.A -> Move(-4, 0),
      KeyCode.D -> Move(4, 0))

    val moves = mutable.Set[Move]()
    document.onkeydown = (event: KeyboardEvent) => availableMoves.get(event.keyCode.toChar.toUpper.toInt).foreach(moves.add)
    document.onkeyup = (event: KeyboardEvent) => availableMoves.get(event.keyCode.toChar.toUpper.toInt).foreach(moves.remove)

    def render(state: Game): Unit = {
      val move = moves.foldLeft(Move(0, 0))(_ + _)
      val stepEnemies = state.enemies.map(e => Enemy(e.x, (e.y + 2) % h))

      val newX = (state.player.x + move.dx).max(0).min(w)
      val newY = (state.player.y + move.dy).max(0).min(h)

      val stepPlayer = state.player.copy(
        x = newX, y = newY,
        alive = stepEnemies.forall { enemy =>
          Math.abs(enemy.x - newX) > 10 || Math.abs(enemy.y - newY) > 10
        }
      )

      val nextState = state.copy(
        player = stepPlayer,
        enemies = stepEnemies)

      React.render(game(nextState), document.getElementById("game"))

      if (nextState.player.alive)
        setTimeout(() => render(nextState), 33)
    }

    render(Game(w, h, Player(w/2, h/2), List.fill(20)(Enemy(x = Random.nextInt(w), y = Random.nextInt(h)))))
  }

  val enemy = ReactComponentB[Enemy]("enemy").render { e =>
    <.svg.circle(
      ^.svg.cx := e.x,
      ^.svg.cy := e.y,
      ^.svg.r  := 5,
      ^.svg.fill := "red").render
  }.build

  val player = ReactComponentB[Player]("player").render { p =>
    val color = if (p.alive) "white" else "gray"
    <.svg.circle(
      ^.svg.cx := p.x,
      ^.svg.cy := p.y,
      ^.svg.r  := 5,
      ^.svg.fill := color).render
  }.build

  val game = ReactComponentB[Game]("game").render { (state) =>
    <.svg.svg(
      ^.background := "black",
      ^.width := state.width,
      ^.height := state.height,
      player(state.player),
      state.enemies.map(enemy(_))).render
  }.build
}