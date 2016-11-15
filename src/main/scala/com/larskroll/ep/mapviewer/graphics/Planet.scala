package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.larskroll.ep.mapviewer.data.{ Planet => PlanetData, Orbiting, Planets, ConstantOriginOrbit, Moons, AstronomicalObject };
import com.larskroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer, Textures };

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import com.outr.scribe.Logging

class Planet(val planet: PlanetData, val radius: Double, val orbiter: Orbiting) extends GraphicsObject with Logging with Overlayed {
    private val geometry = new SphereGeometry(radius, 24.0, 24.0, 0.0, Math.PI * 2, 0.0, Math.PI * 2);

    private val material = new MeshLambertMaterial(Planet.materialParams(planet.name));

    val mesh: Mesh = new Mesh(geometry, material);

    GraphicsObjects.put(mesh, this);

    private val path = orbiter.orbit match {
        case co: ConstantOriginOrbit => co.path(360)
        case _                 => Array[Vector3]()
    };
    private val curve = new CatmullRomCurve3(path.toJSArray);
    private val curveGeometry = {
        val geom = new Geometry();
        geom.vertices = curve.getPoints(360.0).map { p => p.asInstanceOf[Vector3] };
        geom
    };
    private val lineParams = js.Dynamic.literal(
        color = 0xC0C0C0).asInstanceOf[LineBasicMaterialParameters]

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
        children.foreach { c => c.update(t) }
    }

    override def position = mesh.position;

    override def id = mesh.id;

    override def data: Option[AstronomicalObject] = Some(planet);
}

object Planet {

    def materialParams(name: String): MeshLambertMaterialParameters = js.Dynamic.literal(
        color = new Color(Planets.colours(name)) // wireframe = true
        ).asInstanceOf[MeshLambertMaterialParameters]

    def fromPlanetData(planet: PlanetData): Planet = {
        val p = new Planet(planet, planet.radius.toKilometers * Main.scale, planet.asInstanceOf[Orbiting]);
        p.mesh.name = planet.name;
        return p;

    }
}