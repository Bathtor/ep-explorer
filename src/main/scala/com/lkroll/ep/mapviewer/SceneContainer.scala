package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.datamodel.OrbitDistance
import com.lkroll.ep.mapviewer.graphics.GraphicsObject
import com.lkroll.ep.mapviewer.three.{
  Color,
  Object3D,
  PerspectiveCamera,
  Scene,
  Vector3,
  WebGLRenderer,
  WebGLRendererParameters
}

import scala.collection.mutable;

import org.scalajs.dom
import org.scalajs.dom.HTMLElement
import scala.scalajs.js.Dynamic
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
    // The lazy scene is always accessed during construction, so initialise the view here.
    UI.updateView(this, uiInfo, systemTracking);
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

  def markAll(): Unit = {
    localityGroups.foreach {
      case (head, group) => {
        head.activatePathRender();
        group.foreach { entry =>
          entry.activatePathRender();
        }
      }
    }
  }

  def unmarkAll(): Unit = {
    localityGroups.foreach {
      case (head, group) => {
        head.deactivatePathRender();
        group.foreach { entry =>
          entry.deactivatePathRender();
        }
      }
    }
  }

  def markLocal(obj: GraphicsObject): Unit = {
    obj match {
      case opObj: OrbitObject => {
        opObj.activatePathRender();
        localityGroups.get(opObj) match {
          case Some(entries) => {
            entries.foreach { o =>
              o.activatePathRender();
            }
          }
          case None => logger.error(s"No locality entry found for ${obj.name}!")
        }
      }
      case _ => logger.warn(s"Tracking non-orbiting object ${obj.name}")
    }
  }
  def unmarkLocal(obj: GraphicsObject): Unit = {
    obj match {
      case opObj: OrbitObject => {
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
      case _ => logger.warn(s"Untracking non-orbiting object ${obj.name}")
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

  protected def initCamera(): PerspectiveCamera = {
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

  container.style.width = s"${width}px"
  container.style.height = s"${height}px"
  container.style.position = "relative"

  val absolute = "absolute"
  val positionZero = "0"

  protected def initRenderer(): WebGLRenderer = {
    val params = Dynamic
      .literal(antialias = true,
               alpha = true,
               logarithmicDepthBuffer = true)
      .asInstanceOf[WebGLRendererParameters]
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
  def controls: CameraControls

  container.appendChild(renderer.domElement)

  lazy val composer = {
    val ec = new three.EffectComposer(renderer);
    passes.foreach { p =>
      ec.addPass(p)
    };
    ec
  };

  val clearPass = new three.ClearPass(new Color(0xffffff), 1.0);

  val renderPass = new three.RenderPass(scene, camera);
  renderPass.clear = false;

  val outputPass = new three.OutputPass();

  def passes = Seq(clearPass, renderPass, outputPass);

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
    composer.render();
    stats.end();
  }

  def animate(): Unit = {};
}
