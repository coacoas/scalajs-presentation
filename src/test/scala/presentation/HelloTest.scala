package presentation

import utest._
import org.scalajs.jquery.jQuery

object HelloTest extends TestSuite {

  Hello.setupUI()

  def tests = TestSuite {
    'Hello {
      assert(jQuery("p:contains('Hello, JavaScript Dependencies')").length == 1)
    }

    'ButtonClick {
      def messageCount = jQuery("p:contains('You clicked the button')").length

      val button = jQuery("button:contains('Click me')")
      assert(button.length == 1)
      assert(messageCount == 0)

      for (c <- 1 to 5) {
        button.click()
        assert(messageCount == c)
      }
    }
  }
}
