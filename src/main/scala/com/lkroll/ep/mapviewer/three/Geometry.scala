package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("three", "BufferGeometry")
class BufferGeometry extends js.Object {
  def setFromPoints(points: js.Array[Vector3]): BufferGeometry = js.native
  def setAttribute(name: String, attribute: BufferAttribute): BufferGeometry = js.native
  def computeBoundingSphere(): Unit = js.native
  def dispose(): Unit = js.native
}

@js.native
@JSImport("three", "BufferAttribute")
class BufferAttribute extends js.Object {
  var needsUpdate: Boolean = js.native
}

@js.native
@JSImport("three", "Float32BufferAttribute")
class Float32BufferAttribute extends BufferAttribute {
  def this(values: js.Array[Double], itemSize: Int) = this()
}

@js.native
@JSImport("three", "BoxGeometry")
class BoxGeometry extends BufferGeometry {
  def this(width: Double, height: Double, depth: Double) = this()
}

@js.native
@JSImport("three", "ConeGeometry")
class ConeGeometry extends BufferGeometry {
  def this(radius: Double, height: Double, radialSegments: Double) = this()
}

@js.native
@JSImport("three", "CylinderGeometry")
class CylinderGeometry extends BufferGeometry {
  def this(radiusTop: Double, radiusBottom: Double, height: Double, radialSegments: Double, heightSegments: Double, openEnded: Boolean) = this()
}

@js.native
@JSImport("three", "PlaneGeometry")
class PlaneGeometry extends BufferGeometry {
  def this(width: Double, height: Double, widthSegments: Double) = this()
}

@js.native
@JSImport("three", "SphereGeometry")
class SphereGeometry extends BufferGeometry {
  def this(radius: Double, widthSegments: Double, heightSegments: Double) = this()
}

@js.native
@JSImport("three", "TorusGeometry")
class TorusGeometry extends BufferGeometry {
  def this(radius: Double, tube: Double, radialSegments: Double) = this()
  def this(radius: Double, tube: Double, radialSegments: Double, tubularSegments: Double) = this()
}

@js.native
@JSImport("three", "CatmullRomCurve3")
class CatmullRomCurve3 extends js.Object {
  def this(points: js.Array[Vector3]) = this()
  def this(points: js.Array[Vector3], closed: Boolean) = this()
  def getPoints(divisions: Double): js.Array[Vector3] = js.native
}

@js.native
@JSImport("three", "Mesh")
class Mesh extends Object3D {
  def this(geometry: BufferGeometry, material: Material) = this()
  var geometry: BufferGeometry = js.native
  var material: Material = js.native
}

@js.native
@JSImport("three", "Line")
class Line extends Object3D {
  def this(geometry: BufferGeometry, material: Material) = this()
  var geometry: BufferGeometry = js.native
  var material: Material = js.native
}

@js.native
@JSImport("three", "Points")
class Points extends Object3D {
  def this(geometry: BufferGeometry, material: Material) = this()
  var geometry: BufferGeometry = js.native
  var material: Material = js.native
}

@js.native
@JSImport("three", "AxesHelper")
class AxesHelper extends Object3D {
  def this(size: Double) = this()
}

@js.native
@JSImport("three", "ArrowHelper")
class ArrowHelper extends Object3D {
  def this(direction: Vector3, origin: Vector3, length: Double, color: Double) = this()
  def setDirection(direction: Vector3): Unit = js.native
}
