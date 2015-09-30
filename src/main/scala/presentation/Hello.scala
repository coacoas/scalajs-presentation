package presentation

import org.scalajs.dom._
import org.scalajs.jquery.{jQuery => $}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Hello extends JSApp {
  var clicked = 0

  @JSExport
  override def main(): Unit = {
    $(document).ready {
      $("#click-me").click { (ev: Event) =>
        clicked += 1
        $(ev.target).html(s"Clicked $clicked times")
      }
    }
  }
}
