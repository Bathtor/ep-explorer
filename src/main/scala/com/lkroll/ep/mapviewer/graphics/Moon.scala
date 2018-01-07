package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.data.{ Moon => MoonData, Orbiting, Rotating, Moons, AstronomicalObject, Settlements };
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer, Textures }

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

class MoonSingle(val moon: MoonData) extends GraphicsObject {

  val orbiter: Orbiting = moon match {
    case o: Orbiting => o
    case _           => throw new RuntimeException("Moons better orbit^^")
  }

  val rotor: Rotating = moon match {
    case r: Rotating => r
    case _           => throw new RuntimeException("Moons usually rotate, even if very slowly/tidally locked")
  }

  private val (radius, faces) = {
    val r = moon.radius.toKilometers;
    val f = Math.max(Math.floor((2.0 * Math.PI * r) / 100.0), 64.0);
    //println(s"Drawing ${moon.name} with $f faces.");
    (r, f)
  }

  private val geometry = new SphereGeometry(radius, faces, faces);

  private val material = new MeshPhongMaterial(Moon.materialParams(moon.name, true));

  val mesh: Mesh = {
    val m = new Mesh(geometry, material);
    m.name = moon.name;
    GraphicsObjects.put(m, this);
    m
  }

  val arrow = {
    val dir = new Vector3(0.0, 0.0, 0.0);
    val origin = new Vector3(0.0, 0.0, 0.0);
    val ah = new ArrowHelper(dir, origin, radius, 0xFFFF10);
    ah
  }
  val light = new PointLight(0xFFFFFF, 1.0, 0.0);

  val settlements = Settlements.forMoon.getOrElse(moon.id, Seq.empty).map { ao => AtmosphericObject.fromData(ao) };

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

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(moon);

  override def boundingRadius: Double = radius;
}

class Moon(val moon: MoonData) extends GraphicsObject with Overlayed {

  val orbiter: Orbiting = moon match {
    case o: Orbiting => o
    case _           => throw new RuntimeException("Moons better orbit^^")
  }

  val rotor: Rotating = moon match {
    case r: Rotating => r
    case _           => throw new RuntimeException("Moons usually rotate, even if very slowly/tidally locked")
  }

  private val (radius, faces) = {
    val r = moon.radius.toKilometers * Main.scale;
    val f = Math.max(Math.floor((2.0 * Math.PI * r) / 400.0), 24.0);
    (r, f)
  }

  private val geometry = new SphereGeometry(radius, faces, faces);

  private val material = new MeshPhongMaterial(Moon.materialParams(moon.name, false));

  val mesh: Mesh = {
    val m = new Mesh(geometry, material);
    m.name = moon.name;
    GraphicsObjects.put(m, this);
    m
  }

  val overlay = TacticalOverlay.from(moon);
  GraphicsObjects.put(overlay.mesh, this);

  override def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
    overlay.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer) {
    scene.addSceneObject(this, mesh);
    scene.addOverlayObject(this, overlay.mesh);
    children.foreach { c => c.addToScene(scene) }
  }

  override def update(t: Time) {
    val pos = orbiter.orbit.at(t).pos;
    moveTo(pos);
    val m = rotor.rotation.at(t).rotationMatrix;
    mesh.setRotationFromMatrix(m);
    children.foreach { c => c.update(t) }
  }

  override def children = List.empty[GraphicsObject];

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(moon);

  override def boundingRadius: Double = radius;
}

object Moon {

  def materialParams(name: String, transp: Boolean): MeshPhongMaterialParameters = js.Dynamic.literal(
    //color = new Color(Moons.colours(name)), //, wireframe = true
    map = Textures("planet"),
    transparent = transp,
    opacity = 0.5,
    side = THREE.DoubleSide).asInstanceOf[MeshPhongMaterialParameters];

  def fromData(moon: MoonData): Moon = {
    fromData(moon, SystemView).left.get
  }

  def fromData(moon: MoonData, viewType: ViewType): Either[Moon, MoonSingle] = {
    viewType match {
      case SystemView => {
        val m = new Moon(moon);
        Left(m)
      }
      case SingleView => {
        val m = new MoonSingle(moon);
        Right(m)
      }
    }

  }
}
