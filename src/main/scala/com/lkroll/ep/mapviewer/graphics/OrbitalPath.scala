package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D

import com.lkroll.ep.mapviewer.datamodel.{
  AstronomicalObject,
  ConstantOriginOrbit,
  Lagrangian,
  OrbitalSnapshot,
  Orbiting,
  Rotating,
  StaticOrbit
};
import com.lkroll.ep.mapviewer.{ExtObject3D, ExtVector3, Main, SceneContainer, Textures};

import scala.scalajs.js
import js.JSConverters._

import squants._
import squants.space._

import scribe.Logging

object OrbitalPath {
  val SEGMENTS = 360;
}

trait OrbitalPath { self: GraphicsObject =>
  import OrbitalPath._;

  def orbiter: Orbiting;

  def orbitColour: Int;

  private var off: Boolean = true;

  def activatePathRender(): Unit = {
    if (off && !redundant) {
      this.off = false;
      currentOrbit = Some(calculateOrbit(Main.starttime));
      scene.addObject(self, currentOrbit.get);
    }
  }
  def deactivatePathRender(): Unit = {
    if (!off && !redundant) {
      this.off = true;
      scene.removeObject(currentOrbit.get);
      currentOrbit.get.geometry.dispose();
      currentOrbit = None;
    }
  }

  private lazy val fixed: Boolean = orbiter.orbit match {
    case _: StaticOrbit => true
    case _              => false
  };

  private lazy val redundant: Boolean = orbiter.orbit match {
    case _: StaticOrbit => true
    case _: Lagrangian  => true
    case _              => false
  }

  private var currentOrbit: Option[Line] = None;

  private var scene: SceneContainer = null;

  def addEllipseToScene(scene: SceneContainer): Unit = {
    this.scene = scene;
    if (!off) { // just act based on default value
      activatePathRender();
    }
  }

  def updateEllipse(t: Time): Unit = {
    if (redundant || off) {
      return;
    }
    if (!fixed) {
      currentOrbit match {
        case Some(orbit) => {
          val os = orbiter.orbit.at(t);
          val path = this.path(os);
          //val positions = path.map(_.toArray()).flatten.map(_.toFloat).toJSArray;
          //orbit.geometry.asInstanceOf[BufferGeometry].addAttribute("position", new Float32Attribute(positions, 3));
          val vertices = orbit.geometry.vertices;
          for (i <- 0 until SEGMENTS) {
            vertices(i) = path(i);
          }
          orbit.geometry.verticesNeedUpdate = true;
          orbit.geometry.computeBoundingSphere();
        }
        case None => {
          val newOrbit = calculateOrbit(t);
          scene.addObject(self, newOrbit);
          currentOrbit = Some(newOrbit);
        }
      }
      //      val newOrbit = calculateOrbit(t);
      //      scene.removeObject(currentOrbit);
      //      scene.addObject(self, newOrbit);
      //      currentOrbit = newOrbit;
    } // else just leave it where it is
  }

  private def calculateOrbit(t: Time): Line = {
    val os = orbiter.orbit.at(t);
    val path = this.path(os);
    //val curve = this.curve(path);
    //val geom = curveGeometry(curve);
    val geom = curveGeometry(path);
    val ellipse = this.ellipse(geom);
    //ellipse.frustumCulled = false;
    //ellipse.renderOrder = 1;
    ellipse
  }

  private def path(os: OrbitalSnapshot): Array[Vector3] = {
    val path = os.path(SEGMENTS);
    assert(path.size == SEGMENTS);
    path
  }
  //private def curve(path: Array[Vector3]): CatmullRomCurve3 = new CatmullRomCurve3(path.toJSArray);
  //  private def curveGeometry(curve: CatmullRomCurve3): Geometry = {
  //    val geom = new Geometry();
  //    geom.vertices = curve.getPoints(360.0).map { p => p.asInstanceOf[Vector3] };
  //    geom
  //  };
  //val indices = (0 until SEGMENTS).toArray.toJSArray;
  def colours =
    (0 until SEGMENTS)
      .map(i => {
        val colour = new Color(orbitColour);
        // colour.multiplyScalar(2.0 / Math.sqrt(i.toDouble));
        // colour.multiplyScalar(1.0 - 0.0027 * i.toDouble);
        colour.multiplyScalar(1.0 / Math.log(i.toDouble / 2.0));
        colour
      })
      .toJSArray;
  private def curveGeometry(path: Array[Vector3]): Geometry = {
    val geom = new Geometry();
    geom.vertices = path.toJSArray;
    geom.colors = colours;
    //val positions = path.map(_.toArray()).flatten.map(_.toFloat).toJSArray;
    //    geom.addAttribute("position", new Float32Attribute(positions, 3));
    //    geom.addAttribute("color", new Float32Attribute(colours, 3));
    //geom.computeBoundingSphere();
    geom
  };
  //  private val lineParams = js.Dynamic.literal(
  //    color = orbitColour).asInstanceOf[LineBasicMaterialParameters]
  private val lineParams = js.Dynamic
    .literal(vertexColors = THREE.VertexColors, depthTest = false, depthWrite = false)
    .asInstanceOf[LineBasicMaterialParameters];
  private val curveMaterial = new LineBasicMaterial(lineParams);

  // Create the final Object3d to add to the scene
  def ellipse(curveGeometry: Geometry): Line = new Line(curveGeometry, curveMaterial);
}
