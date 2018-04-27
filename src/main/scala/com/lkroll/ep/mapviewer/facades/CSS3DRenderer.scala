package com.lkroll.ep.mapviewer.facades

import org.denigma.threejs._

import scalajs.js
import scalajs.js.annotation._
import scalajs.js.typedarray._
import org.scalajs.dom.Node

@js.native
@JSGlobal("THREE.CSS3DRenderer")
class CSS3DRenderer extends js.Any {

}

@js.native
@JSGlobal("THREE.CSS3DObject")
class CSS3DObject extends Object3D {
  def this(element: Node = js.native) = this();
}

@js.native
@JSGlobal("THREE.CSS3DSprite")
class CSS3DSprite extends CSS3DObject {
  def this(element: Node = js.native) = this();
}
