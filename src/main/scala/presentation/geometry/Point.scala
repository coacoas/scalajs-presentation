package presentation.geometry

case class Point(x: Int, y: Int) {
  def +(other: Point) = Point(x + other.x, y + other.y)
  def /(factor: Int) = Point(x/factor, y/factor)
  def unary_- = Point(-x, -y)


  def near(distance: Int)(other: Point) =
    math.abs(other.x - x) <= distance &&
      math.abs(other.y - y) <= distance
}
