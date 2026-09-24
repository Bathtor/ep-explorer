package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("THREE.Vector2")
class Vector2 extends js.Object {
  def this(x: Double = js.native, y: Double = js.native) = this()
  var x: Double = js.native
  var y: Double = js.native
  def set(x: Double, y: Double): Vector2 = js.native
  def setX(x: Double): Vector2 = js.native
  def setY(y: Double): Vector2 = js.native
  def copy(other: Vector2): Vector2 = js.native
  def subVectors(a: Vector2, b: Vector2): Vector2 = js.native
  def distanceTo(other: Vector2): Double = js.native
  override def clone(): Vector2 = js.native
  def toArray(): js.Array[Double] = js.native
}

@js.native
@JSGlobal("THREE.Vector3")
class Vector3 extends js.Object {
  def this(x: Double = js.native, y: Double = js.native, z: Double = js.native) = this()
  var x: Double = js.native
  var y: Double = js.native
  var z: Double = js.native
  def set(x: Double, y: Double, z: Double): Vector3 = js.native
  def setX(x: Double): Vector3 = js.native
  def setY(y: Double): Vector3 = js.native
  def setZ(z: Double): Vector3 = js.native
  def copy(other: Vector3): Vector3 = js.native
  override def clone(): Vector3 = js.native
  def add(other: Vector3): Vector3 = js.native
  def addVectors(a: Vector3, b: Vector3): Vector3 = js.native
  def sub(other: Vector3): Vector3 = js.native
  def subVectors(a: Vector3, b: Vector3): Vector3 = js.native
  def multiplyScalar(factor: Double): Vector3 = js.native
  def normalize(): Vector3 = js.native
  def length(): Double = js.native
  def distanceTo(other: Vector3): Double = js.native
  def applyMatrix3(matrix: Matrix3): Vector3 = js.native
  def applyMatrix4(matrix: Matrix4): Vector3 = js.native
  def applyQuaternion(quaternion: Quaternion): Vector3 = js.native
  def project(camera: Camera): Vector3 = js.native
  def toArray(): js.Array[Double] = js.native
}

@js.native
@JSGlobal("THREE.Euler")
class Euler extends js.Object {
  def this(x: Double = js.native, y: Double = js.native, z: Double = js.native, order: String = js.native) = this()
  var x: Double = js.native
  var y: Double = js.native
  var z: Double = js.native
}

@js.native
@JSGlobal("THREE.Matrix3")
class Matrix3 extends js.Object {
  def set(n11: Double, n12: Double, n13: Double, n21: Double, n22: Double, n23: Double, n31: Double, n32: Double, n33: Double): Matrix3 = js.native
}

@js.native
@JSGlobal("THREE.Matrix4")
class Matrix4 extends js.Object {
  def identity(): Matrix4 = js.native
  def makeRotationFromEuler(euler: Euler): Matrix4 = js.native
  def makeRotationX(angle: Double): Matrix4 = js.native
  def makeRotationY(angle: Double): Matrix4 = js.native
  def multiply(other: Matrix4): Matrix4 = js.native
  def multiplyMatrices(a: Matrix4, b: Matrix4): Matrix4 = js.native
}

@js.native
@JSGlobal("THREE.Quaternion")
class Quaternion extends js.Object {
  def copy(other: Quaternion): Quaternion = js.native
  def setFromUnitVectors(from: Vector3, to: Vector3): Quaternion = js.native
  def setFromRotationMatrix(matrix: Matrix4): Quaternion = js.native
}

@js.native
@JSGlobal("THREE.Color")
class Color extends js.Object {
  def this(hex: Double) = this()
  def this(r: Double, g: Double, b: Double) = this()
  def set(other: Color): Color = js.native
  def getHex(): Double = js.native
  def multiplyScalar(factor: Double): Color = js.native
}
