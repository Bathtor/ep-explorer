package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.datamodel.{ Planet => PlanetData, Orbiting, Rotating, ConstantOriginOrbit, AstronomicalObject };
import com.lkroll.ep.mapviewer.data.Planets
import com.lkroll.ep.mapviewer.data.Settlements
import com.lkroll.ep.mapviewer.data.Moons
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, ExtVector3, SceneContainer, Textures };

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import scribe.Logging

trait PlanetObject extends GraphicsObject

class PlanetSingle(val planet: PlanetData) extends PlanetObject {

  val orbiter: Orbiting = planet match {
    case o: Orbiting => o
    case _           => throw new RuntimeException("Planets better orbit^^")
  }

  val rotor: Rotating = planet match {
    case r: Rotating => r
    case _           => throw new RuntimeException("Planets usually rotate, even if very slowly")
  }

  private val (radius, faces) = {
    val r = planet.radius.toKilometers;
    val f = 64.0; //Math.Math.max(Math.floor((2.0 * Math.PI * r) / 200.0), 64.0);
    println(s"Drawing ${planet.name} with $f faces.");
    (r, f)
  }

  private val geometry = new SphereGeometry(radius, faces, faces);

  private val material = new MeshPhongMaterial(Planet.materialParams(planet.name, true));

  val mesh: Mesh = {
    val m = new Mesh(geometry, material);
    m.name = planet.name;
    //m.rotateX(Degrees(90.0).toRadians);
    m
  }

  GraphicsObjects.put(mesh, this);

  def name = planet.name;

  val arrow = {
    val dir = new Vector3(0.0, 0.0, 0.0);
    val origin = new Vector3(0.0, 0.0, 0.0);
    val ah = new ArrowHelper(dir, origin, radius, 0xFFFF10);
    ah
  }
  val light = new PointLight(0xFFFFFF, 1.0, 0.0);

  val settlements = Settlements.forPlanet.getOrElse(planet.id, Seq.empty).map { ao => AtmosphericObject.fromData(ao) };

  lazy val children = {
    val cB = List.newBuilder[GraphicsObject];
    cB ++= settlements;
    cB.result()
  }

  override def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer) {
    scene.addSceneObject(this, mesh);
    scene.addObject(this, light);
    if (Main.opts.debug()) {
      scene.addObject(this, arrow);
    }
    children.foreach { c => c.addToScene(scene) }
  }

  val inverseScale = -1.0 / Main.scaleDistance;

  override def update(t: Time) {
    val pos = orbiter.orbit.at(t).pos.clone();
    val dir = pos.clone().normalize();
    pos.multiplyScalar(inverseScale); // direction from planet to sun
    light.moveTo(pos);
    if (Main.opts.debug()) {
      pos.normalize().multiplyScalar(2.0 * radius);
      arrow.moveTo(pos);
      arrow.setDirection(dir);
    }
    val m = rotor.rotation.at(t).rotationMatrix;
    mesh.setRotationFromMatrix(m);

    children.foreach { c => c.update(t) }
  }

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(planet);

  override def boundingRadius: Double = radius;
}

class Planet(val planet: PlanetData) extends PlanetObject with Logging with Overlayed {

  val orbiter: Orbiting = planet match {
    case o: Orbiting => o
    case _           => throw new RuntimeException("Planets better orbit^^")
  }

  val rotor: Rotating = planet match {
    case r: Rotating => r
    case _           => throw new RuntimeException("Planets usually rotate, even if very slowly")
  }

  private val (radius, faces) = {
    val r = planet.radius.toKilometers * Main.scale;
    val f = Math.max(Math.floor((2.0 * Math.PI * r) / 400.0), 24.0);
    (r, f)
  }

  private val geometry = new SphereGeometry(radius, faces, faces);

  private val material = new MeshPhongMaterial(Planet.materialParams(planet.name, false));

  val mesh: Mesh = {
    val m = new Mesh(geometry, material);
    m.name = planet.name;
    GraphicsObjects.put(m, this);
    m

  }

  private val path = orbiter.orbit match {
    case co: ConstantOriginOrbit => co.path(360)
    case _                       => Array[Vector3]()
  };
  private val curve = new CatmullRomCurve3(path.toJSArray);
  private val curveGeometry = {
    val geom = new Geometry();
    geom.vertices = curve.getPoints(360.0).map { p => p.asInstanceOf[Vector3] };
    geom
  };
  private val lineParams = js.Dynamic.literal(
    color = 0xFCD19C).asInstanceOf[LineBasicMaterialParameters]

  private val curveMaterial = new LineBasicMaterial(lineParams);

  // Create the final Object3d to add to the scene
  private val ellipse = new Line(curveGeometry, curveMaterial);

  val moons = Moons.forPlanet.getOrElse(orbiter.name, Seq.empty).map { m => Moon.fromData(m) };

  lazy val children = {
    val cB = List.newBuilder[GraphicsObject];
    cB ++= moons;
    cB.result()
  }

  val overlay = TacticalOverlay.from(planet);

  GraphicsObjects.put(overlay.mesh, this);

  def name = planet.name;

  override def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
    overlay.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer) {
    scene.addSceneObject(this, mesh);
    scene.addOverlayObject(this, overlay.mesh);
    scene.addObject(this, ellipse);
    children.foreach { c => c.addToScene(scene) }
  }

  override def update(t: Time) {
    val pos = orbiter.orbit.at(t).pos;
    moveTo(pos);
    val m = rotor.rotation.at(t).rotationMatrix;
    mesh.setRotationFromMatrix(m);
    children.foreach { c => c.update(t) }
  }

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(planet);

  override def boundingRadius: Double = radius;
}

object Planet {

  def materialParams(name: String, transp: Boolean): MeshPhongMaterialParameters = js.Dynamic.literal(
    //color = new Color(Planets.colours(name)), //, wireframe = true
    map = Textures("planet"),
    transparent = transp,
    opacity = 0.5,
    side = THREE.DoubleSide).asInstanceOf[MeshPhongMaterialParameters];

  def fromData(planet: PlanetData): Planet = {
    fromData(planet, SystemView).left.get
  }

  def fromData(planet: PlanetData, viewType: ViewType): Either[Planet, PlanetSingle] = {
    viewType match {
      case SystemView => {
        val p = new Planet(planet);
        Left(p)
      }
      case SingleView => {
        val p = new PlanetSingle(planet);
        Right(p)
      }
    }

  }
}
