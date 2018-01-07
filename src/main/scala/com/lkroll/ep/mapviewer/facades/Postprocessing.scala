package com.lkroll.ep.mapviewer.facades

import org.denigma.threejs._
//import org.scalajs.dom._

import scalajs.js
import scalajs.js.annotation._
import scalajs.js.typedarray._

@js.native
@JSGlobal("THREE.Pass")
class Pass extends js.Object {
  var enabled: Boolean = js.native;
  var needsSwap: Boolean = js.native;
  var clear: Boolean = js.native;
  var renderToScreen: Boolean = js.native;
}

@js.native
@JSGlobal("THREE.ClearPass")
class ClearPass extends Pass {
  def this(clearColor: Color = js.native, clearAlpha: Double = js.native) = this();
}

@js.native
@JSGlobal("THREE.RenderPass")
class RenderPass extends Pass {
  def this(scene: Scene = js.native, camera: Camera = js.native, overrideMaterial: Material = js.native, clearColor: Color = js.native, clearAlpha: Double = js.native) = this();
}

@js.native
@JSGlobal("THREE.EffectComposer")
class EffectComposer extends js.Object {
  def this(renderer: Renderer = js.native, renderTarget: RenderTarget = js.native) = this();
  def addPass(pass: Pass): Unit = js.native;
  def render(delta: Double = js.native): Unit = js.native;
}

@js.native
@JSGlobal("THREE.ShaderPass")
class ShaderPass extends Pass {
  def this(shader: ShaderMaterial, textureID: String = js.native) = this();
}

@js.native
@JSGlobal("THREE.CopyShader")
object CopyShader extends ShaderMaterial {

}

//@js.native
//trait ShaderUniforms extends js.Object {
//    var tDiffuse: js.Any = js.native
//    var resolution: Vector2 = js.native
//}

@js.native
@JSGlobal("THREE.FXAAShader")
object FXAAShader extends ShaderMaterial {
}

@js.native
@JSGlobal("THREE.TexturePass")
class TexturePass extends Pass {
  def this(map: Texture, opacity: Double = js.native) = this();
}
