package com.lkroll.ep.mapviewer

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D
import org.denigma.threejs.extensions.controls.CameraControls
import org.denigma.threejs.extras.HtmlSprite
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import scala.util.Random

import graphics.{ IntersectionPriorities, GraphicsObject }
import datamodel.{ AstronomicalObject }

import squants.time._

import scribe.Logging

class SingleScene(val targetData: AstronomicalObject, val container: HTMLElement, val width: Double, val height: Double) extends SceneContainer with TimeAnimatedScene with Tracking with Selecting with Logging {

  lazy val target = {
    val t = graphics.objectForData(targetData);
    t.addToScene(this);
    val centre = new Vector3(0.0, 0.0, 0.0);
    t.moveTo(centre)
    t
  }

  lazy val initialTrackingObject: Option[GraphicsObject] = Main.opts.tracking.get.flatMap { ao =>
    val r = this.searchIndex.get(ao.name);
    if (r.isEmpty) {
      logger.warn(s"Tracking object ${ao.name} could not be found in scene.");
    }
    r
  }

  val ctrls = new MapControls(camera, this.container, this, width, height, initialTrackingObject.getOrElse(target), IntersectionPriorities.FirstSmallest);
  override val controls: CameraControls = ctrls;

  override def distance: Double = 3.0 * target.boundingRadius

  lazy val uiInfo = s"Single Body: ${targetData.name}";
  lazy val systemTracking = Some(targetData);
  lazy val sceneParams = QueryParams(View.Single, Main.opts.target(targetData));
  //override def sceneParams: QueryParams = ;

  //    val light = new DirectionalLight(0xffffff, 2)
  //    light.position.set(1, 1, 1).normalize()
  //    scene.add(light)
  val ambLight = new AmbientLight(0xFFFFFF, 0.4);
  scene.add(ambLight);

  val axisHelper = new AxesHelper(distance);

  val plane = {
    val geometry = new PlaneGeometry(3.0 * target.boundingRadius, 3.0 * target.boundingRadius, 2);
    val material = new MeshPhongMaterial(js.Dynamic.literal(
      color = new Color(0xffff00),
      side = THREE.DoubleSide,
      opacity = 0.8,
      transparent = true,
      depthWrite = false).asInstanceOf[MeshPhongMaterialParameters]);
    val p = new Mesh(geometry, material);
    p.name = "XY-plane"
    p
  }

  if (Main.opts.debug()) {
    scene.add(axisHelper);
    scene.add(plane);
  }

  val texturePass = new facades.TexturePass(Textures("background"));

  override def passes = Seq(clearPass, texturePass, renderPass, copyPass);

  private def updatePositions() {
    target.update(time)
  }

  private var running = false;
  private var time = Main.starttime;
  private var deltaTime = Seconds(0.0);

  override def start(): Unit = {
    running = true;
  }
  override def step(): Unit = {
    time += deltaTime;
    updatePositions();
    TimeControls.update(time)
  }
  override def stop(): Unit = {
    running = false;
  }
  override def setSpeed(t: Time): Unit = {
    deltaTime = t;
  }
  override def setOffset(t: Time): Unit = {
    time = t;
  }

  override def animate() {
    if (running) {
      step();
    }
  }

  override def select(obj: graphics.GraphicsObject): Unit = {
    ctrls.select(obj);
  }

  override def track(obj: graphics.GraphicsObject): Unit = {
    ctrls.track(obj);
  }

}
