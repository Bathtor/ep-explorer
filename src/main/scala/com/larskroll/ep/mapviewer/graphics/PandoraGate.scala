package com.larskroll.ep.mapviewer.graphics

import com.larskroll.ep.mapviewer.data.{ PandoraGate => PandoraGateData, AstronomicalObject }
import com.larskroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer }
import org.denigma.threejs._

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

class PandoraGate(val settlement: PandoraGateData) extends GraphicsObject with Overlayed {

    val (height, radius) = {
        val r = 5.0;
        val h = 1.0;
        (h, r)
    };

    protected val geometry = new TorusGeometry(radius, height, 16, 32);
    protected val material = new MeshPhongMaterial(PandoraGate.materialParams(settlement.name));
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
        // TODO move half geometry height up to stand on the surface
        val dir = pSnap.pos.clone();
        dir.normalize();
        val offset = dir.clone();
        offset.multiplyScalar(height/2.0);
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

object PandoraGate {

    def materialParams(name: String): MeshPhongMaterialParameters = js.Dynamic.literal(
        color = new Color(0xAC5334) // wireframe = true
        ).asInstanceOf[MeshPhongMaterialParameters];

    def fromData(data: PandoraGateData): PandoraGate = {
        new PandoraGate(data)
    }
}