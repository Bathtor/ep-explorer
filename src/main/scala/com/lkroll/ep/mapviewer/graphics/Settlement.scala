package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.{Settlement => SettlementData, AstronomicalObject}
import com.lkroll.ep.mapviewer.{ExtObject3D, SceneContainer}
import com.lkroll.ep.mapviewer.three._

import scala.scalajs.js
import squants._

class Settlement(val settlement: SettlementData) extends GraphicsObject with Overlayed {

  val (height, radius) = {
    val r = settlement.size.toKilometers;
    val h = r / 4.0;
    (h, r)
  };

  protected val geometry = new ConeGeometry(radius, height, 32);
  protected val material = new MeshPhongMaterial(Settlement.materialParams(settlement.name));
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

  override def moveTo(pos: Vector3): Unit = {
    mesh.moveTo(pos);
    overlay.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer): Unit = {
    scene.addSceneObject(this, mesh);
    scene.addOverlayObject(this, overlay.mesh);
  }

  val meshRotation = new Quaternion();

  override def update(t: Time): Unit = {
    val pSnap = settlement.position.at(t);
    val dir = pSnap.pos.clone();
    dir.normalize();
    val offset = dir.clone();
    offset.multiplyScalar(height / 2.0);
    offset.add(pSnap.pos);
    moveTo(offset);
    meshRotation.setFromUnitVectors(vYup, dir);
    mesh.setRotationFromQuaternion(meshRotation);
  }

  override def children = List.empty[GraphicsObject];

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  override def data: Option[AstronomicalObject] = Some(settlement);

  override def boundingRadius: Double = height / 2.0 + radius;
}

object Settlement {

  def materialParams(name: String): MeshPhongMaterialParameters =
    js.Dynamic
      .literal(
        color = new Color(0xFCD19C)
      )
      .asInstanceOf[MeshPhongMaterialParameters];

  def fromData(data: SettlementData): Settlement = {
    new Settlement(data)
  }
}
