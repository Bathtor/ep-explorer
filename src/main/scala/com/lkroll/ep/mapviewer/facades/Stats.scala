package com.lkroll.ep.mapviewer.facades

import org.scalajs.dom.Node
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("Stats")
class Stats extends js.Object {
  def showPanel(id: Int): Unit = js.native;
  def begin(): Unit = js.native;
  def end(): Unit = js.native;
  var dom: Node = js.native;
}
