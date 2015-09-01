package presentation

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.jquery.jQuery


object Hello extends JSApp {
  def main() = {
    jQuery("body").append("<p>Hello, JavaScript Dependencies!</p>")
  }
}
