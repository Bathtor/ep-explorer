package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.{
  Lagrangian,
  OrbitalSnapshot,
  Orbiting,
  StaticOrbit
};
import com.lkroll.ep.mapviewer.{Main, SceneContainer};
import com.lkroll.ep.mapviewer.three._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import squants._

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
          orbit.geometry.setFromPoints(path.toJSArray);
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
  private def curveGeometry(path: Array[Vector3]): BufferGeometry = {
    val geom = new BufferGeometry();
    geom.setFromPoints(path.toJSArray);
    val colourComponents = js.Array[Double]();
    for (colour <- colours) {
      colourComponents.push(colour.r, colour.g, colour.b);
    }
    geom.setAttribute("color", new Float32BufferAttribute(colourComponents, 3));
    geom
  };
  //  private val lineParams = js.Dynamic.literal(
  //    color = orbitColour).asInstanceOf[LineBasicMaterialParameters]
  private val lineParams = js.Dynamic
    .literal(vertexColors = true, depthTest = false, depthWrite = false)
    .asInstanceOf[LineBasicMaterialParameters];
  private val curveMaterial = new LineBasicMaterial(lineParams);

  // Create the final Object3d to add to the scene
  def ellipse(curveGeometry: BufferGeometry): Line = new Line(curveGeometry, curveMaterial);
}
