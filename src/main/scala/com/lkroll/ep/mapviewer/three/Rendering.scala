package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom

@js.native
@JSImport("three",JSImport.Namespace)
object THREE extends js.Object {
  val DoubleSide: js.Any = js.native
  val AdditiveBlending: js.Any = js.native
  val NormalBlending: js.Any = js.native
  val NearestFilter: js.Any = js.native
  val LinearMipmapLinearFilter: js.Any = js.native
  val SRGBColorSpace: String = js.native
}

@js.native
@JSImport("three", "Light")
class Light extends Object3D

@js.native
@JSImport("three", "AmbientLight")
class AmbientLight extends Light {
  def this(color: Double, intensity: Double) = this()
}

@js.native
@JSImport("three", "PointLight")
class PointLight extends Light {
  def this(color: Double, intensity: Double, distance: Double) = this()
  var target: Object3D = js.native
}

@js.native
@JSImport("three", "Texture")
class Texture extends js.Object {
  def this(image: js.Any) = this()
  var needsUpdate: Boolean = js.native
  var colorSpace: String = js.native
  var magFilter: js.Any = js.native
  var minFilter: js.Any = js.native
}

@js.native
@JSImport("three", "CanvasTexture")
class CanvasTexture extends Texture {
  def this(canvas: dom.html.Canvas) = this()
}

@js.native
@JSImport("three", "TextureLoader")
class TextureLoader extends js.Object {
  def load(url: String, onLoad: js.Function1[Texture, Unit], onProgress: js.Function1[dom.Event, Unit], onError: js.Function1[dom.Event, Unit]): Texture = js.native
}

@js.native
trait Renderer extends js.Object {
  var domElement: dom.html.Canvas = js.native
  def render(scene: Scene, camera: Camera): Unit = js.native
  def setSize(width: Double, height: Double): Unit = js.native
}

@js.native trait WebGLRendererParameters extends js.Object

@js.native
@JSImport("three", "WebGLRenderer")
class WebGLRenderer extends Renderer {
  def this(params: WebGLRendererParameters) = this()
  def setPixelRatio(ratio: Double): Unit = js.native
}

@js.native trait RenderTarget extends js.Object
