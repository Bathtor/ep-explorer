package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.larskroll.ep.mapviewer.data.{ Moon => MoonData, Orbiting, Moons, AstronomicalObject };
import com.larskroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer }

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import com.outr.scribe.Logging

class Moon(val moon: MoonData, val radius: Double, val orbiter: Orbiting) extends GraphicsObject with Logging with Overlayed {
    private val geometry = new SphereGeometry(radius, 24.0, 24.0, 0.0, Math.PI * 2, 0.0, Math.PI * 2);

    private val material = new MeshLambertMaterial(Moon.materialParams(moon.name));

    val mesh: Mesh = new Mesh(geometry, material);
    mesh.name = moon.name;

    GraphicsObjects.put(mesh, this);

    val overlay = TacticalOverlay.from(moon);

    GraphicsObjects.put(overlay.mesh, this);

    override def moveTo(pos: Vector3) {
        mesh.moveTo(pos);
        overlay.moveTo(pos);
    }

    override def addToScene(scene: SceneContainer) {
        scene.addSceneObject(this, mesh);
        scene.addOverlayObject(this, overlay.mesh);
    }

    override def update(t: Time) {
        val pos = orbiter.orbit.at(t).pos;
        moveTo(pos);
    }

    override def children = List.empty[GraphicsObject];

    override def name = mesh.name;

    override def position = mesh.position;

    override def id = mesh.id;

    override def data: Option[AstronomicalObject] = Some(moon);
}

object Moon {

    def materialParams(name: String): MeshLambertMaterialParameters = js.Dynamic.literal(
        color = new Color(Moons.colours(name)) // wireframe = true
        ).asInstanceOf[MeshLambertMaterialParameters]

    def fromData(moon: MoonData): Moon = {
        val m = new Moon(moon, moon.radius.toKilometers * Main.scale, moon.asInstanceOf[Orbiting]);
        return m;
    }
}