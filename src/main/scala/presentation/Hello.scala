package presentation

import org.scalajs.jquery.{jQuery => $}
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Hello extends JSApp {
  def setupUI(): Unit = {
    val button = $("""<button type="button">Click me!</button>""")
      .click(addClickedMessage _)
      .appendTo($("body"))

    $("body").append("<p>Hello, JavaScript Dependencies!</p>")
  }

  def addClickedMessage(): Unit =
    $("body").append("<p>You clicked the button!</p>")

  def main(): Unit = {
    $(setupUI _)
  }
}
