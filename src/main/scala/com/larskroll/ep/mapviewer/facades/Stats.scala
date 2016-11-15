package com.larskroll.ep.mapviewer.facades

import org.denigma.threejs._
//import org.scalajs.dom._

import scalajs.js
import scalajs.js.annotation._
import scalajs.js.typedarray._
import org.scalajs.dom.Node

@js.native
@JSName("Stats")
class Stats extends js.Object {
    def showPanel(id: Int): Unit = js.native;
    def begin(): Unit = js.native;
    def end(): Unit = js.native;
    var dom: Node = js.native;
}