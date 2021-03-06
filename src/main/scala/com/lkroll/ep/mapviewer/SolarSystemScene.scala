package com.lkroll.ep.mapviewer

import org.denigma.threejs._
import org.denigma.threejs.extensions.controls.CameraControls
import org.denigma.threejs.extras.HtmlSprite
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import scala.util.Random

import graphics._
import data.{Habitats, Planets, Stars}
import datamodel.AstronomicalObject
import scribe.Logging

import squants._

class SolarSystemScene(val container: HTMLElement, val width: Double, val height: Double)
    extends SceneContainer
    with TimeAnimatedScene
    with Tracking
    with Selecting
    with Logging {

  protected def nodeTagFromTitle(title: String) = p(title, `class` := s"ui large message").render

  val centre = new Vector3(0.0, 0.0, 0.0);

  val planets = Planets.list.map(Planet.fromData);
  val habitats = Habitats.list.map(Habitat.fromData);

  var sprites = List.empty[HtmlSprite]

  override def distance: Double = space.AstronomicalUnits(1).toKilometers * Main.scaleDistance

  lazy val uiInfo: String = "Solar System";
  lazy val systemTracking: Option[AstronomicalObject] = None;
  lazy val sceneParams: QueryParams = QueryParams(View.System);

  //scene.background = new Color(0x000000);
  //scene.background = background;

  val ambLight = new AmbientLight(0xFFFFFF, 0.1);
  scene.add(ambLight);
  val sun = Star.fromStarData(Stars.Sol);
  sun.moveTo(centre);
  sun.addToScene(this);
  val markers = DistanceMarkers.default();
  markers.map(_.addToScene(this));

  planets.foreach(p => p.addToScene(this))
  habitats.foreach(p => p.addToScene(this))
  //    planets.zipWithIndex.foreach {
  //        case (p, i) =>
  //            this.sprites = addLabel(p.mesh.position.clone().setY(p.mesh.position.y - 200), "Planet #" + i) :: this.sprites
  //
  //    }
  //    sprites.foreach(cssScene.add)

  // postprocessing
  //				val composer = new EffectComposer( renderer );
  //				composer.addPass( new THREE.RenderPass( scene, camera ) );
  //				val effect1 = new ShaderPass( DotScreenShader );
  //				effect1.uniforms('scale').value = 4;
  //				composer.addPass( effect1 );
  //				val effect2 = new THREE.ShaderPass( THREE.RGBShiftShader );
  //				effect2.uniforms('amount').value = 0.0015;
  //				effect2.renderToScreen = true;
  //				composer.addPass( effect2 );

  val axisHelper = new AxesHelper(1e12);

  if (Main.opts.debug()) {
    scene.add(axisHelper);
  }

  val texturePass = new facades.TexturePass(Textures("background"));

  override def passes = Seq(clearPass, texturePass, renderPass, copyPass);

  val initialTrackingObject: Option[GraphicsObject] = Main.opts.tracking.get.flatMap { ao =>
    val r = this.searchIndex.get(ao.name);
    if (r.isEmpty) {
      logger.warn(s"Tracking object ${ao.name} could not be found in scene.");
    }
    r
  }

  val ctrls = new MapControls(camera,
                              this.container,
                              this,
                              width,
                              height,
                              initialTrackingObject.getOrElse(sun),
                              IntersectionPriorities.FirstLargest); //planets("Saturn").mesh);

  override val controls: CameraControls = ctrls;

  private def updatePositions() {
    sun.update(time)
    planets.foreach { p =>
      p.update(time)
    }
    habitats.foreach { p =>
      p.update(time)
    }
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
  override def currentTime: Time = time;

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
  override def tracked: Option[graphics.GraphicsObject] = {
    Some(ctrls.tracked)
  }
}
