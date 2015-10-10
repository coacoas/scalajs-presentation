package presentation.geometry

import japgolly.nyaya._
import japgolly.nyaya.test.PropTest._
import japgolly.nyaya.test._
import utest._

import utest.ExecutionContext.RunNow


/**
 * Created by bcarlson on 10/3/15.
 */
object PointTest extends TestSuite {
  lazy val point : Gen[Point] = for {
    x <- Gen.int
    y <- Gen.int
  } yield Point(x,y)

  lazy val addition: Prop[Point] = Prop.test("adds", p => p + p == Point(p.x + p.x, p.y + p.y))
  lazy val reflection: Prop[Point] = Prop.test("reflection", p => -p == Point(-p.x, -p.y))

  override def tests = TestSuite {
    'add - (point mustSatisfy addition)
    'reflection - (point mustSatisfy reflection)
  }
}
