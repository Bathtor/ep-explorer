package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.datamodel.{ AstronomicalObject, ExtraUnits, ConstantOriginOrbit }
import com.lkroll.ep.mapviewer.data.Stars
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer };

import squants._
import squants.space._
import squants.time._
import squants.motion._

import scala.scalajs.js
import js.JSConverters._

class DistanceMarker(val distance: Length) extends GraphicsObject {
  import ExtraUnits._;

  val co = ConstantOriginOrbit(0.0, distance, 0.0.º, 0.0.º, 0.0.º, 0.0.º, Stars.Sol.mass, 1.0.kg);
  val path = co.at(Main.starttime).path(360);
  val curve = new CatmullRomCurve3(path.toJSArray);
  val curveGeometry = {
    val geom = new Geometry();
    geom.vertices = curve.getPoints(360.0).map { p => p.asInstanceOf[Vector3] };
    geom
  };
  val lineParams = js.Dynamic.literal(
    color = DistanceMarkers.colour).asInstanceOf[LineBasicMaterialParameters]

  val curveMaterial = new LineBasicMaterial(lineParams);

  // Create the final Object3d to add to the scene
  val ellipse = new Line(curveGeometry, curveMaterial);
  val label = new TextSprite(s"${distance.toAstronomicalUnits}AU");

  override def moveTo(pos: Vector3): Unit = {} // do nothing

  override def addToScene(scene: SceneContainer): Unit = {
    scene.addObject(this, ellipse);
    label.moveTo(path(0));
    scene.addObject(this, label.sprite);
    //    label.moveTo(path(0));
    //    label.addToScene(scene);
  }

  override def update(time: Time): Unit = {} // do nothing

  override def children: List[GraphicsObject] = List.empty;

  override def name: String = s"Marker at ${distance.toAstronomicalUnits}AU";

  override def position: Vector3 = ellipse.position;

  override def id: Double = ellipse.id;

  override def boundingRadius: Double = 0.0;

  override def represents(ao: AstronomicalObject): Boolean = false;
}

object DistanceMarkers {
  import ExtraUnits._;

  val colour = new Color(0x202020);

  def default(): List[DistanceMarker] = {
    val subAU = (1 until 10).map(i => (i.toDouble / 10.0).AU);
    val singleAU = (1 until 10).map(i => (i.toDouble).AU);
    val deciAU = (1 until 10).map(i => (i.toDouble * 10.0).AU);
    val centAU = (1 until 10).map(i => (i.toDouble * 100.0).AU);
    val millAU = (1 until 10).map(i => (i.toDouble * 1000.0).AU);
    val all = List(subAU, singleAU, deciAU, centAU, millAU).flatten;
    all.map(new DistanceMarker(_))
  }
}
