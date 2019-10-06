package com.lkroll.ep.mapviewer.datamodel

import org.denigma.threejs._

import squants.time._
import squants.space._
import squants.motion._

import java.util.UUID

import ExtraUnits._

trait Rotation {
  def at(t: Time): RotationSnapshot;
  def rotationPeriod: Time;
}

trait RotationSnapshot {
  def at: Time;
  def rotationMatrix: Matrix4;
  def project(pos: Vector3): Unit;
}

trait Rotating {
  val rotation: Rotation;
  def name(): String;
  def id(): UUID;
  def radius: Length;
}

/**
  * @param T The time in which the object rotates around its axis by 360º
  * @param tilt The tilt of the axis to the ecliptic of the orbit
  * @param alpha right ascension
  * @param delta declination
  * @param M0 mean anomaly at the J2000 epoch
  */
case object EarthEquatorConstantRotation extends Rotation {

  val M0 = Degrees(0.0); // at J2000 noon the sun is directly over the 0 meridian
  val T = Days(0.99726968);
  val tilt = Degrees(23.44);
  val n = DegreesPerSecond(360.0 / T.toSeconds);
  val delta = Degrees(90.0);

  case class RotationSnap(at: Time, rotationMatrix: Matrix4) extends RotationSnapshot {
    def project(pos: Vector3): Unit = {
      pos.applyMatrix4(rotationMatrix);
    }
  }

  val eclipticRotation = {
    val m = new Matrix4();
    //m2.makeRotationX(tilt.toRadians);
    //m.makeRotationY(alpha.toRadians); // skip because alpha is 0
    m.makeRotationX((delta - tilt).toRadians); // equator is tilt down from the ecliptic and pole is delta up
    m
  }
  def axialRotation(M: Angle): Matrix4 = {
    val m = new Matrix4();
    m.makeRotationY(M.toRadians);
    m
  }
  override def at(t: Time): RotationSnap = {
    val M = (M0 + Degrees(n.toDegreesPerSecond * t.toSeconds)).normalise();
    val aM = axialRotation(M);
    val rM = new Matrix4();
    rM.multiplyMatrices(eclipticRotation, aM);
    RotationSnap(t, rM)
  }
  override def rotationPeriod: Time = T;
}

object TidalLock {
  def apply(orbit: Orbit): Rotation = {
    EquatorialConstantRotation(RotationPeriod(orbit.orbitalPeriod), Degrees(0.0), Degrees(0.0), Degrees(0.0)) // FIXME I guess the axis of rotation should be perpendicular to the plane of the orbit?
  }
}

case class EquatorialConstantRotation(val n: AngularVelocity,
                                      val alpha: Angle,
                                      val delta: Angle,
                                      val M0: Angle,
                                      val retrograde: Boolean = false)
    extends Rotation {

  val T = Seconds(360.0 / n.toDegreesPerSecond);

  case class RotationSnap(at: Time, rotationMatrix: Matrix4) extends RotationSnapshot {
    def project(pos: Vector3): Unit = {
      pos.applyMatrix4(rotationMatrix);
    }
  }

  val eclipticRotation = {
    val m = new Matrix4();
    val m2 = new Matrix4();
    m.makeRotationFromEuler(new Euler(delta.toRadians, 0.0, alpha.toRadians, "ZXY"));
    m2.makeRotationX(-EarthEquatorConstantRotation.tilt.toRadians);
    m2.multiply(m);
    m2
  }
  def axialRotation(M: Angle): Matrix4 = {
    val m = new Matrix4();
    m.makeRotationY(M.toRadians);
    m
  }
  override def at(t: Time): RotationSnap = {
    val M = (if (retrograde) {
               M0 - Degrees(n.toDegreesPerSecond * t.toSeconds)
             } else {
               M0 + Degrees(n.toDegreesPerSecond * t.toSeconds)
             }).normalise();
    val aM = axialRotation(M);
    val rM = new Matrix4();
    rM.multiplyMatrices(eclipticRotation, aM);
    RotationSnap(t, rM)
  }
  def rotationPeriod: Time = T;
}
