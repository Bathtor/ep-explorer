package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.data.{Habitats, Planets, Stars}
import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject
import com.lkroll.ep.mapviewer.graphics._
import com.lkroll.ep.mapviewer.three._

import org.scalajs.dom.HTMLElement
import scalatags.JsDom.all._
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


  override def distance: Double = space.AstronomicalUnits(1).toKilometers * Main.scaleDistance

  lazy val uiInfo: String = "Solar System";
  lazy val systemTracking: Option[AstronomicalObject] = None;
  lazy val sceneParams: QueryParams = QueryParams(View.System);

  val ambLight = new AmbientLight(0xFFFFFF, 0.1);
  scene.add(ambLight);
  val sun = Star.fromStarData(Stars.Sol);
  sun.moveTo(centre);
  sun.addToScene(this);
  val markers = DistanceMarkers.default();
  markers.map(_.addToScene(this));

  planets.foreach(p => p.addToScene(this))
  habitats.foreach(p => p.addToScene(this))
  val axisHelper = new AxesHelper(1e12);

  if (Main.opts.debug()) {
    scene.add(axisHelper);
  }

  val texturePass = new three.TexturePass(Textures("background"));

  override def passes = Seq(clearPass, texturePass, renderPass, outputPass);

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
                              IntersectionPriorities.FirstLargest);

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
