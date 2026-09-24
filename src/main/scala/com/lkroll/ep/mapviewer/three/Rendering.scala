package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

import org.scalajs.dom

@js.native
@JSGlobal("THREE")
object THREE extends js.Object {
  val DoubleSide: js.Any = js.native
  val VertexColors: js.Any = js.native
  val AdditiveBlending: js.Any = js.native
  val NormalBlending: js.Any = js.native
  val NearestFilter: js.Any = js.native
  val LinearMipMapLinearFilter: js.Any = js.native
}

@js.native
@JSGlobal("THREE.Light")
class Light extends Object3D

@js.native
@JSGlobal("THREE.AmbientLight")
class AmbientLight extends Light {
  def this(color: Double, intensity: Double) = this()
}

@js.native
@JSGlobal("THREE.PointLight")
class PointLight extends Light {
  def this(color: Double, intensity: Double, distance: Double) = this()
  var target: Object3D = js.native
}

@js.native
@JSGlobal("THREE.Texture")
class Texture extends js.Object {
  def this(image: js.Any) = this()
  var needsUpdate: Boolean = js.native
  var magFilter: js.Any = js.native
  var minFilter: js.Any = js.native
}

@js.native
@JSGlobal("THREE.TextureLoader")
class TextureLoader extends js.Object {
  def load(url: String, onLoad: js.Function1[Texture, Unit], onProgress: js.Function1[dom.XMLHttpRequest, Unit], onError: js.Function1[dom.XMLHttpRequest, Unit]): Texture = js.native
}

@js.native
trait Renderer extends js.Object {
  var domElement: dom.html.Canvas = js.native
  def render(scene: Scene, camera: Camera): Unit = js.native
  def setSize(width: Double, height: Double): Unit = js.native
}

@js.native trait WebGLRendererParameters extends js.Object

@js.native
@JSGlobal("THREE.WebGLRenderer")
class WebGLRenderer extends Renderer {
  def this(params: WebGLRendererParameters) = this()
  def setPixelRatio(ratio: Double): Unit = js.native
}

@js.native trait RenderTarget extends js.Object
