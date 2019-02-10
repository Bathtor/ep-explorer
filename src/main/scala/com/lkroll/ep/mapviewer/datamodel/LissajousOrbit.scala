package com.lkroll.ep.mapviewer.datamodel

import squants.space._
import squants.time._
import squants.motion._
import squants.mass._

import org.denigma.threejs._

import java.util.UUID;

import com.lkroll.ep.mapviewer.{ Main, ExtVector3 };

import ExtraUnits._

class LissajousOrbit(val centre: Orbit, val eclipticAmplitude: Length, val zAmplitude: Length,
                     val lambda: AngularVelocity, val nu: AngularVelocity, val k: Double,
                     val phi: Angle, val psi: Angle) extends Orbit {

  case class OrbitalPosition(at: Time, pos: Vector3, posRaw: Vector3, val M: Angle, parent: OrbitalSnapshot) extends OrbitalSnapshot {
    override def v: Velocity = MetersPerSecond(Double.NaN); // FIXME: no idea how to calculate this
    override def eclipticMatrix = parent.eclipticMatrix;
    //        lazy val dir = {
    //            val v = new Vector3();
    //            v.subVectors(pos, parent.pos);
    //            v
    //        }
    override def project(pos: Vector3): Unit = {
      parent.project(pos);
      //pos.add(dir);
    }

    private def positionFromt(t: Time): Vector3 = {
      val parent = centre.at(t);
      val M = Degrees(lambda.toDegreesPerSecond * t.toSeconds + phi.toDegrees).normalise();
      val x = -eclipticAmplitude.toKilometers * M.cos;
      val y = k * eclipticAmplitude.toKilometers * M.sin;
      val Mz = Degrees(nu.toDegreesPerSecond * t.toSeconds + psi.toDegrees).normalise();
      val z = zAmplitude.toKilometers * Mz.sin;
      val posRaw = new Vector3();
      posRaw.set(x, y, z);
      val pos = scaledPosition(posRaw, parent);
      pos
    }

    override def path(segments: Int): Array[Vector3] = {
      val cycleLambda = 360.0 / lambda.toDegreesPerSecond;
      val cycleNu = 360.0 / nu.toDegreesPerSecond;
      val cycleMin = Math.min(cycleLambda, cycleNu);
      val twoCycles = cycleMin * 2.0;
      val inc = twoCycles / segments.toDouble;
      val points = (0 to segments) map { i =>
        Seconds(i.toDouble * inc) + at
      } map { t =>
        positionFromt(t)
      };
      points.toArray
    }
  }

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null);

  private def scaledPosition(posRaw: Vector3, parentOrbit: OrbitalSnapshot): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z * Main.scaleDistance);
    parentOrbit.project(pos);
    pos.add(parentOrbit.pos);
    return pos;
  }

  def at(t: Time): OrbitalSnapshot = {
    if (!parameterCache.at.equals(t)) {
      val parent = centre.at(t);
      val M = Degrees(lambda.toDegreesPerSecond * t.toSeconds + phi.toDegrees).normalise();
      val x = -eclipticAmplitude.toKilometers * M.cos;
      val y = k * eclipticAmplitude.toKilometers * M.sin;
      val Mz = Degrees(nu.toDegreesPerSecond * t.toSeconds + psi.toDegrees).normalise();
      val z = zAmplitude.toKilometers * Mz.sin;
      val posRaw = new Vector3();
      posRaw.set(x, y, z);
      val pos = scaledPosition(posRaw, parent);
      //println(s"posRaw=${posRaw.pretty} pos=${pos.pretty}")
      parameterCache = OrbitalPosition(t, pos, posRaw, M, parent)
    }
    return parameterCache;
  }
  def orbitalPeriod: Time = Seconds(Double.PositiveInfinity); // these orbits typically don't repeat
}
