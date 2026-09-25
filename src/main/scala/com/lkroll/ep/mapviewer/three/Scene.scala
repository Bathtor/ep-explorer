package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("three", "Object3D")
class Object3D extends js.Object {
  val id: Double = js.native
  var name: String = js.native
  var position: Vector3 = js.native
  var rotation: Euler = js.native
  var quaternion: Quaternion = js.native
  var scale: Vector3 = js.native
  var up: Vector3 = js.native
  var visible: Boolean = js.native
  var matrix: Matrix4 = js.native
  def add(child: Object3D): Unit = js.native
  def remove(child: Object3D): Unit = js.native
  def lookAt(target: Vector3): Unit = js.native
  def setRotationFromQuaternion(value: Quaternion): Unit = js.native
  def setRotationFromMatrix(value: Matrix4): Unit = js.native
  def rotateX(angle: Double): Object3D = js.native
  def rotateY(angle: Double): Object3D = js.native
  def rotateZ(angle: Double): Object3D = js.native
}

@js.native
@JSImport("three", "Scene")
class Scene extends Object3D

@js.native
@JSImport("three", "Camera")
class Camera extends Object3D {
  var projectionMatrix: Matrix4 = js.native
}

@js.native
@JSImport("three", "PerspectiveCamera")
class PerspectiveCamera extends Camera {
  def this(fov: Double, aspect: Double, near: Double, far: Double) = this()
}

@js.native
trait Intersection extends js.Object {
  val distance: Double = js.native
  val `object`: Object3D = js.native
}

@js.native
@JSImport("three", "Raycaster")
class Raycaster extends js.Object {
  def setFromCamera(coords: Vector2, camera: Camera): Unit = js.native
  def intersectObjects(objects: js.Array[Object3D]): js.Array[Intersection] = js.native
}
