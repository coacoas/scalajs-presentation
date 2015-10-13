package presentation.geometry

import org.scalajs.dom.ext.Color

case class Circle(center: Point, radius: Int, color: Color) {
  def colorString: String = f"#${color.r}%02x${color.g}%02x${color.b}%02x".toUpperCase
}
