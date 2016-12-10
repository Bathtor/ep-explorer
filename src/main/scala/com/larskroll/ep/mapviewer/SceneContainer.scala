package com.larskroll.ep.mapviewer

import org.denigma.threejs.extensions.controls.{ HoverControls, CameraControls }
import org.denigma.threejs.extras.HtmlRenderer
import org.denigma.threejs.{ Object3D, Color, Vector2, Vector3, Scene, WebGLRendererParameters, WebGLRenderer, PerspectiveCamera, Renderer }
import org.scalajs.dom.{ MouseEvent, Event }
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js.{ Array, Dynamic }

import com.larskroll.ep.mapviewer.graphics.GraphicsObject

trait SceneContainer {

    def container: HTMLElement;

    def width: Double;

    def height: Double;

    val sceneObjects = scala.collection.mutable.ArrayBuffer.empty[Object3D];
    val overlayObjects = scala.collection.mutable.ArrayBuffer.empty[Object3D];
    val searchIndex = scala.collection.mutable.Map.empty[String, GraphicsObject];

    lazy val scene = {
        val s = new Scene();
        // some init code here as this will definitely get loaded
        UI.updateView(this, uiInfo);
        // end init
        s
    }
    
    def uiInfo: String;

    def addSceneObject(obj: GraphicsObject, mesh: Object3D) {
        sceneObjects += mesh;
        scene.add(mesh);
        searchIndex += (obj.name -> obj)
        UI.addData(obj.name);
    }

    def addOverlayObject(obj: GraphicsObject, mesh: Object3D) {
        overlayObjects += mesh;
        scene.add(mesh);
    }

    def addObject(obj: GraphicsObject, mesh: Object3D) {
        scene.add(mesh);
    }

    def distance: Double = 2000.0 * Main.scaleDistance;

    lazy val renderer: WebGLRenderer = this.initRenderer()

    lazy val camera = initCamera()

    def aspectRatio: Double = width / height

    protected def initCamera() =
        {
            val fov = 60;
            val near = Main.scaleDistance;
            val far = 1e12;
            val camera = new PerspectiveCamera(fov, this.aspectRatio, near, far);
            camera.position.z = distance;
            camera.up = new Vector3(0, 0, 1);
            camera
        }

    protected def onEnterFrameFunction(double: Double): Unit = {
        onEnterFrame()
        render()
    }

    def render(): Int = dom.window.requestAnimationFrame(onEnterFrameFunction _)

    container.style.width = width.toString
    container.style.height = height.toString
    container.style.position = "relative"

    val absolute = "absolute"
    val positionZero = "0"

    protected def initRenderer() = {
        val params = Dynamic.literal(
            antialias = true,
            alpha = true, // canvas = container
            logarithmicDepthBuffer = true).asInstanceOf[WebGLRendererParameters]
        val vr = new WebGLRenderer(params)
        vr.domElement.style.position = absolute
        vr.domElement.style.top = positionZero
        vr.domElement.style.left = positionZero
        vr.domElement.style.margin = positionZero
        vr.domElement.style.padding = positionZero
        vr.setPixelRatio(Main.pixelRatio);
        vr.setSize(width, height)
        vr
    }
    val cssScene = new Scene()

    val cssRenderer: HtmlRenderer = this.initCSSRenderer

    protected def initCSSRenderer = {
        val rendererCSS = new HtmlRenderer()
        rendererCSS.setSize(width, height)
        rendererCSS.domElement.style.position = absolute
        rendererCSS.domElement.style.top = positionZero
        rendererCSS.domElement.style.left = positionZero
        rendererCSS.domElement.style.margin = positionZero
        rendererCSS.domElement.style.padding = positionZero
        rendererCSS
    }

    val controls: CameraControls = new HoverControls(camera, this.container)

    container.appendChild(renderer.domElement)
    //container.appendChild(cssRenderer.domElement)
    // cssRenderer.domElement.appendChild( renderer.domElement )

    lazy val composer = {
        val ec = new facades.EffectComposer(renderer);
        passes.foreach { p => ec.addPass(p) };
        ec
    };

    val clearPass = new facades.ClearPass(new Color(0xffffff), 1.0);

    val renderPass = new facades.RenderPass(scene, camera);
    renderPass.clear = false;

    //    val aaShader = facades.FXAAShader;
    //    aaShader.uniforms = Dynamic.literal("tDiffuse" -> Dynamic.literal(value = null), "resolution" -> Dynamic.literal(value = new Vector2(1/width, 1/height)));
    //    val aaPass = new facades.ShaderPass(aaShader);
    //    aaPass.renderToScreen = true;
    val copyPass = new facades.ShaderPass(facades.CopyShader);
    copyPass.renderToScreen = true;

    def passes = Seq(clearPass, renderPass, copyPass);

    val stats = {
        val s = new facades.Stats();
        s.showPanel(0);
        this.container.appendChild(s.dom);
        s
    }

    private[SceneContainer] def onEnterFrame(): Unit = {
        stats.begin();
        animate();
        controls.update()
        //renderer.render(scene, camera)
        composer.render();
        //cssRenderer.render(cssScene, camera)
        stats.end();
    }

    def animate() {};
}