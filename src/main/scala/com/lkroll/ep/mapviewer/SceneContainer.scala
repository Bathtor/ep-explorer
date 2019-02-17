package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.datamodel.OrbitDistance
import org.denigma.threejs.extensions.controls.{ HoverControls, CameraControls }
import org.denigma.threejs.extras.HtmlRenderer
import org.denigma.threejs.{ Object3D, Color, Vector2, Vector3, Scene, WebGLRendererParameters, WebGLRenderer, PerspectiveCamera, Renderer }
import org.scalajs.dom.{ MouseEvent, Event }
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js.{ Array, Dynamic }
import collection.mutable;

import com.lkroll.ep.mapviewer.graphics.GraphicsObject

import scribe.Logging

trait SceneContainer extends Logging {

  type OrbitObject = GraphicsObject with graphics.OrbitalPath;

  def container: HTMLElement;

  def width: Double;

  def height: Double;

  val localityGroups = com.lkroll.common.collections.HashSetMultiMap.empty[OrbitObject, OrbitObject];
  val sceneObjects = mutable.ArrayBuffer.empty[Object3D];
  val overlayObjects = mutable.ArrayBuffer.empty[Object3D];
  val searchIndex = mutable.Map.empty[String, GraphicsObject];

  lazy val scene = {
    val s = new Scene();
    // some init code here as this will definitely get loaded
    UI.updateView(this, uiInfo, systemTracking);
    // end init
    s
  }

  def uiInfo: String;
  def systemTracking: Option[datamodel.AstronomicalObject];
  def sceneParams: QueryParams;

  def addSceneObject(obj: GraphicsObject, mesh: Object3D): Unit = {
    sceneObjects += mesh;
    scene.add(mesh);
    searchIndex += (obj.name -> obj)
    UI.addData(obj.name);
    addLocal(obj);
  }

  def markLocal(obj: GraphicsObject): Unit = {
    obj match {
      case opObj: OrbitObject =>
        {
          opObj.activatePathRender();
          //logger.info(s"Activating ${opObj.name} and the following local objects:");
          localityGroups.get(opObj) match {
            case Some(entries) => {
              entries.foreach { o =>
                o.activatePathRender();
                //logger.info(s"	${o.name}");
              }
            }
            case None => logger.error(s"No locality entry found for ${obj.name}!")
          }
        }
      case _ => logger.warn(s"Tracking non-orbiting object ${obj.name}") //
    }
  }
  def unmarkLocal(obj: GraphicsObject): Unit = {
    obj match {
      case opObj: OrbitObject =>
        {
          opObj.deactivatePathRender()
          localityGroups.get(opObj) match {
            case Some(entries) => {
              entries.foreach { o =>
                o.deactivatePathRender();
              }
            }
            case None => logger.error(s"No locality entry found for ${obj.name}!")
          }
        }
      case _ => logger.warn(s"Untracking non-orbiting object ${obj.name}") //
    }
  }

  private def addLocal(obj: GraphicsObject): Unit = {
    obj match {
      case opObj: OrbitObject => {
        var newEntries = List.empty[OrbitObject];
        val thisOrbit = opObj.orbiter.orbit;
        // FIXME please
        localityGroups.keySet.foreach { other =>
          val thatOrbit = other.orbiter.orbit;
          val distanceForward = thisOrbit.pathTo(thatOrbit);
          val distanceBackward = thatOrbit.pathTo(thisOrbit);
          //          logger.info(s"""
          //  Forward ${opObj.name} -> ${other.name} = ${distanceForward}
          //  Backward ${opObj.name} <- ${other.name} = ${distanceBackward}
          //""");
          distanceForward match {
            case OrbitDistance.Zero | OrbitDistance.Similar => {
              newEntries ::= other;
            }
            case p: OrbitDistance.Path => {
              if (Main.renderUp && (p.upLength == 1) && (p.downLength <= 1)) {
                newEntries ::= other;
              } else if ((opObj.orbiter == data.Stars.Sol) && (p.downLength <= 1)) { // don't go 2 steps down for Sol
                newEntries ::= other; // this path never happens as Sol gets added first
              } else if ((p.upLength == 0) && (p.downLength <= 2)) {
                newEntries ::= other;
              }
            }
            case OrbitDistance.Infinite => () // ignore
          }
          distanceBackward match {
            case OrbitDistance.Zero | OrbitDistance.Similar => {
              localityGroups += (other -> opObj);
            }
            case p: OrbitDistance.Path => {
              if (Main.renderUp && (p.upLength == 1) && (p.downLength) <= 1) {
                localityGroups += (other -> opObj);
              } else if ((other.orbiter == data.Stars.Sol) && (p.downLength <= 1)) { // don't go 2 steps up for Sol
                //logger.info(s"Sol backward path is ${distanceBackward} with DOWN=${p.downLength}");
                localityGroups += (other -> opObj);
              } else if ((p.upLength == 0) && (p.downLength <= 2)) {
                localityGroups += (other -> opObj);
              }
            }
            case OrbitDistance.Infinite => () // ignore
          }
        }
        localityGroups.putAll(opObj, newEntries);
      }
      case _ => {
        () // ignore as we won't have to render paths anyway
      }
    }
  }

  def addOverlayObject(obj: GraphicsObject, mesh: Object3D): Unit = {
    overlayObjects += mesh;
    scene.add(mesh);
  }

  def addObject(obj: GraphicsObject, mesh: Object3D): Unit = {
    scene.add(mesh);
  }
  def removeObject(mesh: Object3D): Unit = {
    scene.remove(mesh);
  }

  def addCSSObject(obj: GraphicsObject, mesh: Object3D): Unit = {
    scene.add(mesh);
  }

  def distance: Double = 2000.0 * Main.scaleDistance;

  lazy val renderer: WebGLRenderer = this.initRenderer();

  lazy val camera = initCamera();

  def aspectRatio: Double = width / height

  protected def initCamera(): PerspectiveCamera =
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

  def render(): Int = dom.window.requestAnimationFrame(onEnterFrameFunction _);

  container.style.width = width.toString
  container.style.height = height.toString
  container.style.position = "relative"

  val absolute = "absolute"
  val positionZero = "0"

  protected def initRenderer(): WebGLRenderer = {
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
  val cssScene = new Scene();

  val cssRenderer: HtmlRenderer = this.initCSSRenderer;

  protected def initCSSRenderer: HtmlRenderer = {
    val rendererCSS = new HtmlRenderer()
    rendererCSS.setSize(width, height)
    rendererCSS.domElement.style.position = absolute
    rendererCSS.domElement.style.top = positionZero
    rendererCSS.domElement.style.left = positionZero
    rendererCSS.domElement.style.margin = positionZero
    rendererCSS.domElement.style.padding = positionZero
    rendererCSS
  }

  val controls: CameraControls = new HoverControls(camera, this.container);

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
