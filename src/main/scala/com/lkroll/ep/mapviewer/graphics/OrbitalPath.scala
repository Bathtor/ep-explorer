package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.datamodel.{ Orbiting, Rotating, OrbitalSnapshot, AstronomicalObject, StaticOrbit, ConstantOriginOrbit, Lagrangian };
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, ExtVector3, SceneContainer, Textures };

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import scribe.Logging

trait OrbitalPath { self: GraphicsObject =>
  def orbiter: Orbiting;

  def orbitColour: Int;

  lazy val constantOrigin: Boolean = orbiter.orbit match {
    case _: StaticOrbit         => true
    case _: ConstantOriginOrbit => true
    case _                      => false
  };

  lazy val redundant: Boolean = orbiter.orbit match {
    case _: StaticOrbit => true
    case _: Lagrangian  => true
    case _              => false
  }

  var currentOrbit: Line = null;

  var scene: SceneContainer = null;

  def addEllipseToScene(scene: SceneContainer): Unit = {
    if (redundant) { return ; }
    this.scene = scene;
    currentOrbit = calculateOrbit(Main.starttime);
    scene.addObject(self, currentOrbit);
  }
  def updateEllipse(t: Time): Unit = {
    if (redundant) { return ; }
    if (!constantOrigin) {
      val newOrbit = calculateOrbit(t);
      scene.removeObject(currentOrbit);
      scene.addObject(self, newOrbit);
      currentOrbit = newOrbit;
    } // else just leave it where it is
  }

  private def calculateOrbit(t: Time): Line = {
    val os = orbiter.orbit.at(t);
    val path = this.path(os);
    val curve = this.curve(path);
    val geom = curveGeometry(curve);
    val ellipse = this.ellipse(geom);
    ellipse
  }

  private def path(os: OrbitalSnapshot): Array[Vector3] = os.path(360);
  private def curve(path: Array[Vector3]): CatmullRomCurve3 = new CatmullRomCurve3(path.toJSArray);
  private def curveGeometry(curve: CatmullRomCurve3): Geometry = {
    val geom = new Geometry();
    geom.vertices = curve.getPoints(360.0).map { p => p.asInstanceOf[Vector3] };
    geom
  };
  private val lineParams = js.Dynamic.literal(
    color = orbitColour).asInstanceOf[LineBasicMaterialParameters]

  private val curveMaterial = new LineBasicMaterial(lineParams);

  // Create the final Object3d to add to the scene
  def ellipse(curveGeometry: Geometry): Line = new Line(curveGeometry, curveMaterial);
}
