package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.larskroll.ep.mapviewer.data.{ Star => StarData, AstronomicalObject, Orbiting };
import com.larskroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer };

import scala.scalajs.js

import squants._
import squants.space._
import squants.time._
import squants.motion._

class Star(val star: StarData, val colour: String, val radius: Double, val orbiter: Orbiting) extends GraphicsObject with Overlayed {
    private val geometry = new SphereGeometry(radius, 24.0, 24.0, 0.0, Math.PI * 2, 0.0, Math.PI * 2);

    private val materialParams = js.Dynamic.literal(
        color = 0xFCF8C2, wireframe = false, transparent = true).asInstanceOf[MeshBasicMaterialParameters]

    private val material = new MeshBasicMaterial(materialParams);

    val mesh: Mesh = new Mesh(geometry, material);
    mesh.name = star.name;

    mesh.up.set(0, 0, 1);

    GraphicsObjects.put(mesh, this);

    val overlay = TacticalOverlay.from(star);

    GraphicsObjects.put(overlay.mesh, this);

    val light = new PointLight(0xFFFFFF, 1.0, 0.0);

    private val rotation = DegreesPerSecond(14.7 / 86400.0);

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

    def update(time: Time) {
        moveTo(orbiter.orbit.at(time).pos);
        //mesh.rotateZ(rotation.toDegreesPerSecond * Main.timeFactor.toSeconds);
    }

    override def children = List.empty[GraphicsObject];

    override def name = star.name;

    override def position = mesh.position;

    override def id = mesh.id;

    override def data: Option[AstronomicalObject] = Some(star);

}

object Star {
    def fromStarData(star: StarData): Star = {
        val s = new Star(star, "yellow", star.radius.toKilometers * Main.scale, star.asInstanceOf[Orbiting]);
        return s;
    }
}