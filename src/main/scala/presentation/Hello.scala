package presentation

import scala.scalajs.js.JSApp

import org.scalajs.dom
import dom.document
import scala.scalajs.js.annotation.JSExport

object Hello extends JSApp {
  def appendPar(targetNode: dom.Node, text: String) = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  @JSExport
  def addClickedMessage() =
    appendPar(document.body, "You clicked the button!")

  def main() = appendPar(document.body, "Hello, World!")
}
