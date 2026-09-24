package com.lkroll.ep.mapviewer.three

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.HTMLElement

@js.native
@JSImport("three/addons/controls/OrbitControls.js", "OrbitControls")
class OrbitControls(camera: Camera, element: HTMLElement) extends js.Object {
  var target: Vector3 = js.native
  var enableDamping: Boolean = js.native
  var dampingFactor: Double = js.native
  var maxAzimuthAngle: Double = js.native
  var minAzimuthAngle: Double = js.native
  def listenToKeyEvents(element: HTMLElement): Unit = js.native
  def update(): Unit = js.native
}

@js.native
@JSImport("three/addons/postprocessing/Pass.js", "Pass")
class Pass extends js.Object {
  var enabled: Boolean = js.native
  var needsSwap: Boolean = js.native
  var clear: Boolean = js.native
  var renderToScreen: Boolean = js.native
}

@js.native
@JSImport("three/addons/postprocessing/ClearPass.js", "ClearPass")
class ClearPass extends Pass {
  def this(clearColor: Color = js.native, clearAlpha: Double = js.native) = this()
}

@js.native
@JSImport("three/addons/postprocessing/RenderPass.js", "RenderPass")
class RenderPass extends Pass {
  def this(scene: Scene = js.native, camera: Camera = js.native, overrideMaterial: Material = js.native, clearColor: Color = js.native, clearAlpha: Double = js.native) = this()
}

@js.native
@JSImport("three/addons/postprocessing/EffectComposer.js", "EffectComposer")
class EffectComposer extends js.Object {
  def this(renderer: Renderer = js.native, renderTarget: RenderTarget = js.native) = this()
  def addPass(pass: Pass): Unit = js.native
  def render(delta: Double = js.native): Unit = js.native
}

@js.native
@JSImport("three/addons/postprocessing/TexturePass.js", "TexturePass")
class TexturePass extends Pass {
  def this(map: Texture, opacity: Double = js.native) = this()
}

@js.native
@JSImport("three/addons/postprocessing/OutputPass.js", "OutputPass")
class OutputPass extends Pass
