package presentation

import scala.scalajs.js.JSApp

import org.scalajs.dom
import dom.document

object Hello extends JSApp {
  def appendPar(targetNode: dom.Node, text: String) = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  def main() = appendPar(document.body, "Hello, World!")
}
