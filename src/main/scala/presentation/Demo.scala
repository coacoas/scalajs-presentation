package presentation

import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive.Observable
import org.scalajs.dom
import org.scalajs.dom.ext.Color
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.concurrent.duration._
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

case class Point(x: Int, y: Int) {
  def +(p: Point) = Point(x + p.x, y + p.y)
  def -(p: Point) = Point(x - p.x, y - p.y)
  def /(d: Int) = Point(x / d, y / d)
}

case class Rectangle(x: Int, y: Int, width: Int, height: Int)

@JSExport
class Demo {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = canvas.parentElement.clientWidth
    canvas.height = canvas.parentElement.clientHeight

    val corners = findCorners(canvas)
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    Observable.interval(20.seconds).foreach { _ => clear(ctx) }

    Observable.repeatTask(corners(Random.nextInt(3)))
      .scan(corners(0)) { (last, corner) => (last + corner) / 2 }
      .foreach(drawPoint(ctx))
  }

  def clear(ctx: CanvasRenderingContext2D) = {
    ctx.fillStyle = "black"
    ctx.fillRect(ctx.canvas.clientLeft, ctx.canvas.clientTop, ctx.canvas.width, ctx.canvas.height)
  }

  def findCorners(canvas: html.Canvas): Vector[Point] = {
    val height = math.min(canvas.height, canvas.width)
    val center = Point(canvas.width / 2, canvas.height / 2)

    val left = center.x - (height / 2)
    val top = center.y - (height / 2)
    val right = center.x + (height / 2)
    val bottom = center.y + (height / 2)

    Vector(
      Point(left, bottom),
      Point(center.x, top),
      Point(right, bottom))
  }

  def drawPoint(ctx: CanvasRenderingContext2D)(point: Point) = {
    def color(p: Point) = {
      val normalize = (ctx.canvas.width * 2) / (ctx.canvas.height.toDouble + p.y)
      val r = (p.x * normalize).toInt
      val g = ((255 * normalize / 2) - p.x).toInt
      val b = (255 * p.y / ctx.canvas.height.toDouble).toInt
      Color(r, g, b)
    }

    ctx.fillStyle = color(point).toString
    ctx.fillRect(point.x, point.y, 1, 1)
  }

  def randomColor: Color = {
    val r = Random.nextInt(255)
    val g = Random.nextInt(255)
    val b = Random.nextInt(255)
    Color(r, g, b)
  }
}
