package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("three", "Material")
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
@JSImport("three", "MeshBasicMaterial")
class MeshBasicMaterial extends Material {
  def this(params: MeshBasicMaterialParameters) = this()
}

@js.native
@JSImport("three", "MeshLambertMaterial")
class MeshLambertMaterial extends Material {
  def this(params: MeshLambertMaterialParameters) = this()
}

@js.native
@JSImport("three", "MeshPhongMaterial")
class MeshPhongMaterial extends Material {
  def this(params: MeshPhongMaterialParameters) = this()
  var emissive: Color = js.native
}

@js.native
@JSImport("three", "LineBasicMaterial")
class LineBasicMaterial extends Material {
  def this(params: LineBasicMaterialParameters) = this()
}

@js.native
@JSImport("three", "PointsMaterial")
class PointsMaterial extends Material {
  def this(params: PointsMaterialParameters) = this()
  var size: Double = js.native
}

@js.native
@JSImport("three", "ShaderMaterial")
class ShaderMaterial extends Material
