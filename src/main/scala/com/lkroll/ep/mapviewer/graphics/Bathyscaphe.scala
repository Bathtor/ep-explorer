package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.{Bathyscaphe => BathyscapheData, AstronomicalObject}
import com.lkroll.ep.mapviewer.{ExtObject3D, Main, SceneContainer}
import org.denigma.threejs._

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

class Bathyscaphe(val settlement: BathyscapheData) extends GraphicsObject with Overlayed {

  val radius = 5.0;

  protected val geometry = new SphereGeometry(radius, 12, 8);
  protected val material = new MeshLambertMaterial(Bathyscaphe.materialParams(settlement.name));
  val mesh = {
    val m = new Mesh(geometry, material);
    m.name = settlement.name;
    GraphicsObjects.put(m, this);
    m
  }

  lazy val overlay = {
    val o = TacticalOverlay.from(settlement);
    GraphicsObjects.put(o.mesh, this);
    o
  }

  override def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
    overlay.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer) {
    scene.addSceneObject(this, mesh);
    scene.addOverlayObject(this, overlay.mesh);
  }

  val meshRotation = new Quaternion();

  override def update(t: Time) {
    val pSnap = settlement.position.at(t);
    val dir = pSnap.pos.clone();
    dir.normalize();
    val offset = dir.clone();
    offset.multiplyScalar(-radius);
    offset.add(pSnap.pos);
    moveTo(offset);
    //dir.multiplyScalar(-1.0); // face down instead of up
    meshRotation.setFromUnitVectors(vYup, dir);
    mesh.setRotationFromQuaternion(meshRotation);
  }

  override def children = List.empty[GraphicsObject];

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(settlement);

  override def boundingRadius: Double = radius;
}

object Bathyscaphe {
  def materialParams(name: String): MeshLambertMaterialParameters =
    js.Dynamic
      .literal(
        color = new Color(0xFCD19C) // wireframe = true
      )
      .asInstanceOf[MeshLambertMaterialParameters];

  def fromData(data: BathyscapheData): Bathyscaphe = {
    new Bathyscaphe(data)
  }
}
