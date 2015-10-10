package presentation.canvas

import org.scalajs.dom
import org.scalajs.dom.html._
import presentation.geometry.Point

import scala.util.Random

class Sierpinski {
  def triangle(canvas: Canvas) = {
    canvas.height = 255
    canvas.width = 255
    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    var count = 0
    var p = Point(0, 0)
    val corners = Seq(Point(255, 255), Point(0, 255), Point(128, 0))

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, 255, 255)
    }

    def run = for (i <- 0 until 10){
      if (count % 3000 == 0) clear()
      count += 1
      p = (p + corners(Random.nextInt(3))) / 2

      val height = 512.0 / (255 + p.y)
      val r = (p.x * height).toInt
      val g = ((255-p.x) * height).toInt
      val b = p.y
      ctx.fillStyle = s"rgb($g, $r, $b)"

      ctx.fillRect(p.x, p.y, 1, 1)
    }

    dom.setInterval(() => run, 50)
  }
}
