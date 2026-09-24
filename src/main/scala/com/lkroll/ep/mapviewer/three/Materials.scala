package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("THREE.Material")
class Material extends js.Object {
  var color: Color = js.native
  var opacity: Double = js.native
  var transparent: Boolean = js.native
}

@js.native trait MeshBasicMaterialParameters extends js.Object
@js.native trait MeshLambertMaterialParameters extends js.Object
@js.native trait MeshPhongMaterialParameters extends js.Object
@js.native trait LineBasicMaterialParameters extends js.Object
@js.native trait PointsMaterialParameters extends js.Object

@js.native
@JSGlobal("THREE.MeshBasicMaterial")
class MeshBasicMaterial extends Material {
  def this(params: MeshBasicMaterialParameters) = this()
}

@js.native
@JSGlobal("THREE.MeshLambertMaterial")
class MeshLambertMaterial extends Material {
  def this(params: MeshLambertMaterialParameters) = this()
}

@js.native
@JSGlobal("THREE.MeshPhongMaterial")
class MeshPhongMaterial extends Material {
  def this(params: MeshPhongMaterialParameters) = this()
  var emissive: Color = js.native
}

@js.native
@JSGlobal("THREE.LineBasicMaterial")
class LineBasicMaterial extends Material {
  def this(params: LineBasicMaterialParameters) = this()
}

@js.native
@JSGlobal("THREE.PointsMaterial")
class PointsMaterial extends Material {
  def this(params: PointsMaterialParameters) = this()
  var size: Double = js.native
}

@js.native
@JSGlobal("THREE.ShaderMaterial")
class ShaderMaterial extends Material
