package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("THREE.Geometry")
class Geometry extends js.Object {
  var vertices: js.Array[Vector3] = js.native
  var colors: js.Array[Color] = js.native
  var verticesNeedUpdate: Boolean = js.native
  def computeBoundingSphere(): Unit = js.native
  def dispose(): Unit = js.native
}

@js.native
@JSGlobal("THREE.BoxGeometry")
class BoxGeometry extends Geometry {
  def this(width: Double, height: Double, depth: Double) = this()
}

@js.native
@JSGlobal("THREE.ConeGeometry")
class ConeGeometry extends Geometry {
  def this(radius: Double, height: Double, radialSegments: Double) = this()
}

@js.native
@JSGlobal("THREE.CylinderGeometry")
class CylinderGeometry extends Geometry {
  def this(radiusTop: Double, radiusBottom: Double, height: Double, radialSegments: Double, heightSegments: Double, openEnded: Boolean) = this()
}

@js.native
@JSGlobal("THREE.PlaneGeometry")
class PlaneGeometry extends Geometry {
  def this(width: Double, height: Double, widthSegments: Double) = this()
}

@js.native
@JSGlobal("THREE.SphereGeometry")
class SphereGeometry extends Geometry {
  def this(radius: Double, widthSegments: Double, heightSegments: Double) = this()
}

@js.native
@JSGlobal("THREE.TorusGeometry")
class TorusGeometry extends Geometry {
  def this(radius: Double, tube: Double, radialSegments: Double) = this()
  def this(radius: Double, tube: Double, radialSegments: Double, tubularSegments: Double) = this()
}

@js.native
@JSGlobal("THREE.CatmullRomCurve3")
class CatmullRomCurve3 extends js.Object {
  def this(points: js.Array[Vector3]) = this()
  def getPoints(divisions: Double): js.Array[Vector3] = js.native
}

@js.native
@JSGlobal("THREE.Mesh")
class Mesh extends Object3D {
  def this(geometry: Geometry, material: Material) = this()
  var geometry: Geometry = js.native
  var material: Material = js.native
}

@js.native
@JSGlobal("THREE.Line")
class Line extends Object3D {
  def this(geometry: Geometry, material: Material) = this()
  var geometry: Geometry = js.native
  var material: Material = js.native
}

@js.native
@JSGlobal("THREE.Points")
class Points extends Object3D {
  def this(geometry: Geometry, material: Material) = this()
  var geometry: Geometry = js.native
  var material: Material = js.native
}

@js.native
@JSGlobal("THREE.AxesHelper")
class AxesHelper extends Object3D {
  def this(size: Double) = this()
}

@js.native
@JSGlobal("THREE.ArrowHelper")
class ArrowHelper extends Object3D {
  def this(direction: Vector3, origin: Vector3, length: Double, color: Double) = this()
  def setDirection(direction: Vector3): Unit = js.native
}
