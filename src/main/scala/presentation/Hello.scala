package presentation

import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Hello extends JSApp {
  @JSExport
  def addClickedMessage() =
    jQuery("body").append("<p>You clicked the button!</p>")

  def main() = 
    jQuery("body").append("<p>Hello, JavaScript Dependencies!</p>")
}
