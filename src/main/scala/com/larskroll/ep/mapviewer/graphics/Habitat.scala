package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.larskroll.ep.mapviewer.data.{ Habitat => HabitatData, Orbiting, Habitats, ONeillCyliner, BernalSphere, ModifiedBernalSphere, Cluster, Torus, ModifiedTorus, Asteroid, AstronomicalObject, UnkownStation };
import com.larskroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer }

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import com.outr.scribe.Logging

abstract class Habitat(val habitat: HabitatData, val orbiter: Orbiting) extends GraphicsObject with Logging with Overlayed {

    protected def geometry: Geometry;
    protected def mesh: Mesh;

    protected val material = new MeshLambertMaterial(Habitat.materialParams(habitat.name));

    lazy val overlay = {
        val o = TacticalOverlay.from(habitat);
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

    override def update(t: Time) {
        val pos = orbiter.orbit.at(t).pos;
        moveTo(pos);
    }

    override def children = List.empty[GraphicsObject];

    override def name = mesh.name;

    override def position = mesh.position;

    override def id = mesh.id;

    override def data: Option[AstronomicalObject] = Some(habitat);
}

class CylindricalHabitat(habitat: HabitatData, orbiter: Orbiting, val length: Double, val radius: Double) extends Habitat(habitat, orbiter) {
    protected val geometry = new CylinderGeometry(radius, radius, length, 24.0, 1.0, false);
    protected val mesh: Mesh = {
        val m = new Mesh(geometry, material);
        m.name = habitat.name;
        GraphicsObjects.put(m, this);
        m
    }
    override def boundingRadius: Double = length / 2.0 + radius;
}

class SphericalHabitat(habitat: HabitatData, orbiter: Orbiting, val radius: Double) extends Habitat(habitat, orbiter) {
    protected val geometry = new SphereGeometry(radius, 24.0, 24.0);
    protected val mesh: Mesh = {
        val m = new Mesh(geometry, material);
        m.name = habitat.name;
        GraphicsObjects.put(m, this);
        m
    }
    override def boundingRadius: Double = radius;
}

class ToroidalHabitat(habitat: HabitatData, orbiter: Orbiting, val radius: Double, val thickness: Double) extends Habitat(habitat, orbiter) {
    protected val geometry = new TorusGeometry(radius, thickness, 24.0);
    protected val mesh: Mesh = {
        val m = new Mesh(geometry, material);
        m.name = habitat.name;
        GraphicsObjects.put(m, this);
        m
    }
    override def boundingRadius: Double = radius + thickness;
}

class AsteroidHabitat(habitat: HabitatData, orbiter: Orbiting, val length: Double, val width: Double, val height: Double) extends Habitat(habitat, orbiter) {
    protected val geometry = new BoxGeometry(length, width, height);
    protected val mesh: Mesh = {
        val m = new Mesh(geometry, material);
        m.name = habitat.name;
        GraphicsObjects.put(m, this);
        m
    }
    override def boundingRadius: Double = Math.max(length, Math.max(width, height));
}

object Habitat extends Logging {

    def materialParams(name: String): MeshLambertMaterialParameters = js.Dynamic.literal(
        color = new Color(0xC0C0C0) // wireframe = true
        ).asInstanceOf[MeshLambertMaterialParameters]

    def fromData(habitat: HabitatData): Habitat = {
        habitat.stationType match {
            case ONeillCyliner(length, radius) => {
                new CylindricalHabitat(habitat, habitat.asInstanceOf[Orbiting], length.toKilometers * Main.scale, radius.toKilometers * Main.scale);
            }
            case Cluster => { // FIXME come up with a better way to represent clusters
                new CylindricalHabitat(habitat, habitat.asInstanceOf[Orbiting], 100.0 * Main.scale, 10.0 * Main.scale);
            }
            case UnkownStation => { // FIXME come up with a better way to represent clusters
                new SphericalHabitat(habitat, habitat.asInstanceOf[Orbiting], 1.0 * Main.scale)
            }
            case BernalSphere(radius) => {
                new SphericalHabitat(habitat, habitat.asInstanceOf[Orbiting], radius.toKilometers * Main.scale)
            }
            case ModifiedBernalSphere(radius) => {
                new SphericalHabitat(habitat, habitat.asInstanceOf[Orbiting], radius.toKilometers * Main.scale)
            }
            case Torus(radius, thickness) => {
                new ToroidalHabitat(habitat, habitat.asInstanceOf[Orbiting], radius.toKilometers * Main.scale, thickness.toKilometers * Main.scale)
            }
            case ModifiedTorus(radius, thickness) => {
                new ToroidalHabitat(habitat, habitat.asInstanceOf[Orbiting], radius.toKilometers * Main.scale, thickness.toKilometers * Main.scale)
            }
            case Asteroid(_, length, width, height) => {
                new AsteroidHabitat(habitat, habitat.asInstanceOf[Orbiting], length.toKilometers * Main.scale, width.toKilometers * Main.scale, height.toKilometers * Main.scale)
            }
            case x => logger.error(s"Unkown habitat type: ${x.getClass}"); null
        }
    }
}