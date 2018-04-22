package com.lkroll.ep.mapviewer.datamodel

import squants.space._
import squants.time._
import squants.motion._
import squants.mass._

import org.denigma.threejs._

import java.util.UUID;

import com.lkroll.ep.mapviewer.{ Main, ExtVector3 };

trait Lagrangian extends Orbit {

}

class L12(val largerBody: Orbiting, val smallerBody: Orbiting, val sign: Double) extends Lagrangian {

  case class OrbitalPosition(at: Time, pos: Vector3, posRaw: Vector3, r: Length, parent: OrbitalSnapshot) extends OrbitalSnapshot {
    def v: Velocity = smallerBody.orbit.at(at).v; // FIXME: this is wrong, of course, only the angular velocity is the same
    def M = parent.M;
    def eclipticMatrix = parent.eclipticMatrix;

    def project(pos: Vector3): Unit = {
      parent.project(pos);
    }
  }

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null);

  lazy val massRatio3Root = {
    val massRatio = smallerBody.mass / (3.0 * largerBody.mass);
    Math.pow(massRatio, 1.0 / 3.0)
  };

  private def radius(R: Length): Length = R * massRatio3Root;

  private def scaledPosition(posRaw: Vector3, smallerOrbit: OrbitalSnapshot, largerOrbit: OrbitalSnapshot): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    smallerOrbit.project(pos);
    pos.add(largerOrbit.pos);
    return pos;
  }

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val osS = smallerBody.orbit.at(t);
      val osL = largerBody.orbit.at(t);
      val posS = osS.posRaw;
      val direction = new Vector3();
      direction.copy(posS);
      val dist = direction.length();
      direction.normalize();
      val r = radius(Kilometers(dist));
      direction.multiplyScalar(sign * r.toKilometers);
      val posRaw = new Vector3();
      posRaw.addVectors(posS, direction);
      val pos = scaledPosition(posRaw, osS, osL);
      parameterCache = OrbitalPosition(t, pos, posRaw, r, osS);
    }
    return parameterCache;
  }

  def orbitalPeriod: Time = smallerBody.orbit.orbitalPeriod;
}

class L1(largerBody: Orbiting, smallerBody: Orbiting) extends L12(largerBody, smallerBody, -1.0)
class L2(largerBody: Orbiting, smallerBody: Orbiting) extends L12(largerBody, smallerBody, 1.0)

class L3(val largerBody: Orbiting, val smallerBody: Orbiting) extends Lagrangian {

  case class OrbitalPosition(at: Time, pos: Vector3, posRaw: Vector3, r: Length, parent: OrbitalSnapshot) extends OrbitalSnapshot {
    def v: Velocity = smallerBody.orbit.at(at).v; // FIXME: this is wrong, of course, only the angular velocity is the same
    def M = parent.M;
    def eclipticMatrix = parent.eclipticMatrix;

    def project(pos: Vector3): Unit = {
      parent.project(pos);
    }
  }

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null);

  lazy val massRatio = (7.0 * smallerBody.mass) / (12.0 * largerBody.mass);

  private def radius(R: Length): Length = R * massRatio;

  private def scaledPosition(posRaw: Vector3, smallerOrbit: OrbitalSnapshot, largerOrbit: OrbitalSnapshot): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    smallerOrbit.project(pos);
    pos.add(largerOrbit.pos);
    return pos;
  }

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val osS = smallerBody.orbit.at(t);
      val osL = largerBody.orbit.at(t);
      val posS = osS.posRaw;
      val direction = new Vector3();
      direction.copy(posS);
      val dist = direction.length();
      direction.normalize();
      val r = radius(Kilometers(dist));
      val rDir = direction.clone();
      rDir.multiplyScalar(1.0 * r.toKilometers);
      direction.multiplyScalar(-1.0 * dist);
      val posRaw = new Vector3();
      posRaw.add(direction); // go to the opposite side from the centre
      posRaw.add(rDir); // and then r back towards the larger body
      val pos = scaledPosition(posRaw, osS, osL);
      parameterCache = OrbitalPosition(t, pos, posRaw, r, osS);
    }
    return parameterCache;
  }

  def orbitalPeriod: Time = smallerBody.orbit.orbitalPeriod;
}

class L45(val largerBody: Orbiting, val smallerBody: Orbiting, val backwards: Boolean) extends Lagrangian {

  case class OrbitalPosition(at: Time, pos: Vector3, posRaw: Vector3, parent: OrbitalSnapshot) extends OrbitalSnapshot {
    def v: Velocity = smallerBody.orbit.at(at).v; // FIXME: this is wrong, of course, only the angular velocity is the same
    def M = parent.M;
    def eclipticMatrix = parent.eclipticMatrix;

    def project(pos: Vector3): Unit = {
      parent.project(pos);
    }
  }

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null);

  private def scaledPosition(posRaw: Vector3, smallerOrbit: OrbitalSnapshot, largerOrbit: OrbitalSnapshot): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    smallerOrbit.project(pos);
    pos.add(largerOrbit.pos);
    return pos;
  }

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val osS = smallerBody.orbit.at(t);
      val osL = largerBody.orbit.at(t);
      val posS = osS.posRaw;
      val direction = new Vector3();
      direction.copy(posS);
      if (backwards) {
        direction.applyMatrix3(Constants.rotateMinus60DZ);
      } else {
        direction.applyMatrix3(Constants.rotate60DZ);
      }
      val posRaw = new Vector3();
      posRaw.add(direction);
      val pos = scaledPosition(posRaw, osS, osL);
      parameterCache = OrbitalPosition(t, pos, posRaw, osS);
    }
    return parameterCache;
  }

  def orbitalPeriod: Time = smallerBody.orbit.orbitalPeriod;
}

class L4(largerBody: Orbiting, smallerBody: Orbiting) extends L45(largerBody, smallerBody, false)
class L5(largerBody: Orbiting, smallerBody: Orbiting) extends L45(largerBody, smallerBody, true)
