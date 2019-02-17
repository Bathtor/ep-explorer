package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.datamodel.{ Star => StarData, AstronomicalObject, Orbiting, Rotating, ExtraUnits, ConstantOriginOrbit };
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer };

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._
import squants.time._
import squants.motion._

class Star(val star: StarData) extends GraphicsObject with Overlayed with OrbitalPath {

  import ExtraUnits._

  override val orbitColour: Int = 0x000000; // not going to be drawn anyway

  override val orbiter: Orbiting = star match {
    case o: Orbiting => o
    case _           => throw new RuntimeException("Stars better orbit^^")
  }

  val rotor: Rotating = star match {
    case r: Rotating => r
    case _           => throw new RuntimeException("Stars usually rotate, even if very slowly")
  }

  private val (radius, faces) = {
    val r = star.radius.toKilometers * Main.scale;
    val f = Math.max(Math.floor((2.0 * Math.PI * r) / 400.0), 24.0);
    (r, f)
  }

  private val geometry = new SphereGeometry(radius, faces, faces);

  val color: Color = star.temperature.toRGB();

  private val materialParams = js.Dynamic.literal(
    color = color, wireframe = false, transparent = true).asInstanceOf[MeshBasicMaterialParameters]

  private val material = new MeshBasicMaterial(materialParams);

  val mesh: Mesh = new Mesh(geometry, material);
  mesh.name = star.name;

  mesh.up.set(0, 0, 1);

  GraphicsObjects.put(mesh, this);

  val overlay = TacticalOverlay.from(star);

  GraphicsObjects.put(overlay.mesh, this);

  val light = new PointLight(color.getHex(), 1.0, 0.0);

  def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
    light.moveTo(pos);
    overlay.moveTo(pos);
  }

  def addToScene(scene: SceneContainer) {
    scene.addSceneObject(this, mesh);
    scene.addOverlayObject(this, overlay.mesh);
    scene.addObject(this, light);
  }

  def update(t: Time) {
    moveTo(orbiter.orbit.at(t).pos);
    val m = rotor.rotation.at(t).rotationMatrix;
    mesh.setRotationFromMatrix(m);
  }

  override def children = List.empty[GraphicsObject];

  override def name = star.name;

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(star);

  override def boundingRadius: Double = radius;

}

object Star {
  def fromStarData(star: StarData, viewType: ViewType = SystemView): Star = {
    val s = new Star(star);
    return s;
  }
}
