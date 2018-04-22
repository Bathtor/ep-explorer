package com.lkroll.ep.mapviewer.datamodel

//import squants._;
import squants.space._
import squants.time._
import squants.motion._
import squants.mass._

import org.denigma.threejs._

import java.util.UUID;

import com.lkroll.ep.mapviewer.Main;

import ExtraUnits._

trait Orbit {
  def at(t: Time): OrbitalSnapshot;
  def orbitalPeriod: Time;
}

trait OrbitalSnapshot {
  def at: Time;
  def M: Angle;
  def pos: Vector3;
  def posRaw: Vector3;
  def v: Velocity;
  def eclipticMatrix: Matrix4;
  def project(pos: Vector3): Unit;
}

trait Orbiting {
  val orbit: Orbit;
  def name(): String;
  def id(): UUID;
  def mass: Mass;
}

case class StaticOrbit(pos: Vector3) extends Orbit {

  val M = Degrees(0.0);
  val v = MetersPerSecond(0.0);
  val eclipticMatrix = {
    val m = new Matrix4();
    m.identity();
    m
  }

  case class OrbitalPosition(at: Time) extends OrbitalSnapshot {
    override def M: Angle = StaticOrbit.this.M;
    def pos: Vector3 = StaticOrbit.this.pos;
    def posRaw: Vector3 = StaticOrbit.this.pos;
    def v: Velocity = StaticOrbit.this.v;
    def eclipticMatrix: Matrix4 = StaticOrbit.this.eclipticMatrix;
    def project(pos: Vector3): Unit = {
      pos.applyMatrix4(eclipticMatrix);
    }
  }

  def at(t: Time): OrbitalSnapshot = OrbitalPosition(t);
  def orbitalPeriod: Time = Seconds(0.0);
}

/**
 * Descriptor for an orbit
 *
 * See {@link: https://en.wikipedia.org/wiki/Orbital_elements}
 *
 * @param e Eccentricity
 * @param a Semimajor Axis
 * @param i Inclination
 * @param Omega Longitude of the ascending node
 * @param omega Argument of periapsis
 * @param M0 Mean Anomaly at epoch 0
 * @param m1 Mass of the orbiting body
 * @param m2 Mass of the orbited body
 */
case class ConstantOriginOrbit(val e: Double, val a: Length, val i: Angle, val Omega: Angle, val omega: Angle, val M0: Angle, val m1: Mass, val m2: Mass, val retrograde: Boolean = false) extends Orbit {

  case class OrbitalPosition(at: Time, M: Angle, E: Angle, nu: Angle, pos: Vector3, posRaw: Vector3, v: Velocity) extends OrbitalSnapshot {
    def eclipticMatrix = eulerMatrix;
    def project(pos: Vector3) = {
      pos.applyMatrix4(eulerMatrix);
    }
  }

  val mu = Constants.G * m1.toKilograms; // gravitational parameter
  val T = Seconds(2 * Math.PI * Math.sqrt(Math.pow(a.toMeters, 3) / (mu + Constants.G * m2.toKilograms)));
  val n = DegreesPerSecond(360.0 / T.toSeconds);

  private lazy val eulerMatrix = {
    val m = (new Matrix4);
    val m2 = (new Matrix4);
    m.makeRotationFromEuler(new Euler(i.toRadians, 0.0, Omega.toRadians, "ZXY"));
    m2.makeRotationFromEuler(new Euler(0.0, 0.0, omega.toRadians, "ZXY"));
    m.multiply(m2);
    //        m.set(Omega.cos * omega.cos - Omega.sin * i.cos * omega.sin,
    //            Omega.sin * omega.cos + Omega.cos * i.cos * omega.sin,
    //            i.sin * omega.sin,
    //            0.0,
    //            -Omega.cos * omega.sin - Omega.sin * i.cos * omega.cos,
    //            -Omega.sin * omega.sin + Omega.cos * i.cos * omega.cos,
    //            i.sin * omega.cos,
    //            0.0,
    //            i.sin * Omega.sin,
    //            -i.sin * Omega.cos,
    //            i.cos,
    //            0.0,
    //            0.0, 0.0, 0.0, 0.1);
    //        m.set( // row 1
    //            Omega.cos * omega.cos - Omega.sin * i.cos * omega.sin,
    //            -Omega.cos * omega.sin - Omega.sin * i.cos * omega.cos,
    //            i.sin * Omega.sin,
    //            0.0,
    //            // row 2
    //            Omega.sin * omega.cos + Omega.cos * i.cos * omega.sin,
    //            -Omega.sin * omega.sin + Omega.cos * i.cos * omega.cos,
    //            -i.sin * omega.cos,
    //            0.0,
    //            // row 3
    //            i.sin * omega.sin,
    //            i.sin * Omega.cos,
    //            i.cos,
    //            0.0,
    //            // row 4
    //            0.0, 0.0, 0.0, 0.1);

    //        val half = 1.0/2.0;
    //        val phi1 = Omega;
    //        val phi2 = i;
    //        val phi3 = omega;
    //        val phi2half = phi2*half;
    //        val phi1p3half = half*(phi1+phi3);
    //        val phi1m3half = half*(phi1-phi3);
    //        val q = new Quaternion(
    //                phi2half.cos*phi1p3half.cos,
    //                phi2half.sin*phi1m3half.cos,
    //                phi2half.sin*phi1m3half.sin,
    //                phi2half.cos*phi1p3half.sin
    //                );
    //        m.makeRotationFromQuaternion(q);
    m
  };

  private val e2 = e * e;
  private val dire = 1.0 - e2;
  private val l: Length = a * dire;
  private val rap = l / (1.0 - e);
  private val rper = l / (1.0 + e)
  private val fak = Math.sqrt(dire);
  private val b = a * fak;
  private val focalDiff = a - rper;

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null, null, null);

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val M = meanAnomaly(t);
      val E = eccentricAnomaly(M);
      val nu = trueAnomalyFromE(E);
      val rawPos = rawPositionFromE(E);
      val pos = scaledPosition(rawPos);
      val r = radius(nu);
      val v = speed(r);
      parameterCache = OrbitalPosition(t, M, E, nu, pos, rawPos, v)
    }
    return parameterCache;
  }

  def orbitalPeriod = T;

  private def meanAnomaly(t: Time): Angle = {
    val M = if (retrograde) {
      M0 - Degrees(n.toDegreesPerSecond * t.toSeconds);
    } else {
      M0 + Degrees(n.toDegreesPerSecond * t.toSeconds);
    }
    M.normalise()
  }

  private def radius(nu: Angle): Length = {
    val ev: Double = 1.0 + e * nu.cos;
    l * (1.0 / ev)
  }

  private def speed(r: Length): Velocity = {
    val v = Math.sqrt(mu * ((2.0 / r.toMeters) - (1.0 / a.toMeters)));
    MetersPerSecond(v)
  }

  private def eccentricAnomaly(M: Angle): Angle = {
    var i = 0;
    val m = M.toRadians;
    var E = if (e < 0.8) { m } else { Math.PI };
    var F = E - e * Math.sin(E) - m;

    while ((Math.abs(F) > Constants.delta) && (i < Constants.maxIter)) {
      E = E - F / (1.0 - e * Math.cos(E));
      F = E - e * Math.sin(E) - m;
      i += 1;
    }
    val Erounded = Math.round(E * Constants.accPow) / Constants.accPow;
    return Radians(Erounded).normalise();
  }

  private def trueAnomalyFromE(E: Angle): Angle = {
    val s = E.sin;
    val c = E.cos;
    val phi = Math.atan2(fak * s, c - e) / Constants.k;

    val nu = Math.round(phi * Constants.accPow) / Constants.accPow;

    return Degrees(nu).normalise();
  }

  private def rawPositionFromE(E: Angle): Vector3 = {
    //val x = a * (E.cos - e); // why -e?
    val x = a * E.cos;
    val y = b * E.sin;
    return new Vector3(x.toKilometers - focalDiff.toKilometers, y.toKilometers, 0.0);
  }

  private def scaledPosition(posRaw: Vector3): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    pos.applyMatrix4(eulerMatrix);
    return pos;
  }

  private def positionFromE(E: Angle): Vector3 = {
    val raw = rawPositionFromE(E);
    scaledPosition(raw)
  }

  private def positionFromM(M: Angle): Vector3 = {
    val E = eccentricAnomaly(M);
    positionFromE(E)
  }

  def path(segments: Int): Array[Vector3] = {
    val inc = 360.0 / segments.doubleValue();
    val points = (0 to segments) map { i =>
      Degrees(i.doubleValue() * inc);
    } map { M =>
      positionFromM(M)
    };
    points.toArray
  }

}

/**
 * Descriptor for an orbit
 *
 * See {@link: https://en.wikipedia.org/wiki/Orbital_elements}
 *
 * @param e Eccentricity
 * @param a Semimajor Axis
 * @param i Inclination
 * @param Omega Longitude of the ascending node
 * @param omega Argument of periapsis
 * @param M0 Mean Anomaly at epoch 0
 * @param m1 Mass of the orbiting body
 * @param centre Orbited body
 * @param retrograde whether or not the orbit is going counter clock wise, so to say
 */
case class ConstantOrbit(val e: Double, val a: Length, val i: Angle, val Omega: Angle, val omega: Angle, val M0: Angle, val m1: Mass, val centre: Orbiting, val retrograde: Boolean = false) extends Orbit {

  case class OrbitalPosition(at: Time, M: Angle, E: Angle, nu: Angle, pos: Vector3, posRaw: Vector3, v: Velocity, parentOrbit: OrbitalSnapshot) extends OrbitalSnapshot {
    def eclipticMatrix = eulerMatrix;
    def project(pos: Vector3) = {
      parentOrbit.project(pos);
      pos.applyMatrix4(eulerMatrix);
    }
  }

  val mu = Constants.G * m1.toKilograms; // gravitational parameter
  val centreMu = Constants.G * centre.mass.toKilograms;
  val T = Seconds(2 * Math.PI * Math.sqrt(Math.pow(a.toMeters, 3) / (mu + centreMu)));
  val n = DegreesPerSecond(360.0 / T.toSeconds);

  private lazy val eulerMatrix = {
    val m = (new Matrix4);
    val m2 = (new Matrix4);
    m.makeRotationFromEuler(new Euler(i.toRadians, 0.0, Omega.toRadians, "ZXY"));
    m2.makeRotationFromEuler(new Euler(0.0, 0.0, omega.toRadians, "ZXY"));
    m.multiply(m2);
  }

  private val e2 = e * e;
  private val dire = 1.0 - e2;
  private val l: Length = a * dire;
  private val rap = l / (1.0 - e);
  private val rper = l / (1.0 + e)
  private val fak = Math.sqrt(dire);
  private val b = a * fak;
  private val focalDiff = a - rper;

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null, null, null, null);

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val osP = centre.orbit.at(t);
      val M = meanAnomaly(t);
      val E = eccentricAnomaly(M);
      val nu = trueAnomalyFromE(E);
      val rawPos = rawPositionFromE(E);
      val pos = scaledPosition(rawPos, osP);
      val r = radius(nu);
      val v = speed(r);
      parameterCache = OrbitalPosition(t, M, E, nu, pos, rawPos, v, osP)
    }
    return parameterCache;
  }

  def orbitalPeriod = T;

  private def meanAnomaly(t: Time): Angle = {
    val M = if (retrograde) {
      M0 - Degrees(n.toDegreesPerSecond * t.toSeconds);
    } else {
      M0 + Degrees(n.toDegreesPerSecond * t.toSeconds);
    }
    M.normalise()
  }

  private def radius(nu: Angle): Length = {
    val ev: Double = 1.0 + e * nu.cos;
    l * (1.0 / ev)
  }

  private def speed(r: Length): Velocity = {
    val v = Math.sqrt(mu * ((2.0 / r.toMeters) - (1.0 / a.toMeters)));
    MetersPerSecond(v)
  }

  private def eccentricAnomaly(M: Angle): Angle = {
    var i = 0;
    val m = M.toRadians;
    var E = if (e < 0.8) { m } else { Math.PI };
    var F = E - e * Math.sin(E) - m;

    while ((Math.abs(F) > Constants.delta) && (i < Constants.maxIter)) {
      E = E - F / (1.0 - e * Math.cos(E));
      F = E - e * Math.sin(E) - m;
      i += 1;
    }
    val Erounded = Math.round(E * Constants.accPow) / Constants.accPow;
    return Radians(Erounded).normalise();
  }

  private def trueAnomalyFromE(E: Angle): Angle = {
    val s = E.sin;
    val c = E.cos;
    val phi = Math.atan2(fak * s, c - e) / Constants.k;

    val nu = Math.round(phi * Constants.accPow) / Constants.accPow;

    return Degrees(nu).normalise();
  }

  private def rawPositionFromE(E: Angle): Vector3 = {
    val x = a * E.cos;
    val y = b * E.sin;
    return new Vector3(x.toKilometers - focalDiff.toKilometers, y.toKilometers, 0.0);
  }

  private def scaledPosition(posRaw: Vector3, osP: OrbitalSnapshot): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    osP.project(pos)
    pos.applyMatrix4(eulerMatrix);
    pos.add(osP.pos);
    return pos;
  }

  //    private def positionFromE(E: Angle): Vector3 = {
  //        val raw = rawPositionFromE(E);
  //        scaledPosition(raw)
  //    }
  //
  //    private def positionFromM(M: Angle): Vector3 = {
  //        val E = eccentricAnomaly(M);
  //        positionFromE(E)
  //    }
  //
  //    def path(segments: Int): Array[Vector3] = {
  //        val inc = 360.0 / segments.doubleValue();
  //        val points = (0 to segments) map { i =>
  //            Degrees(i.doubleValue() * inc);
  //        } map { M =>
  //            positionFromM(M)
  //        };
  //        points.toArray
  //    }

}

sealed trait Progression {
  val direction: Double;
}
object Progressive extends Progression {
  val direction = 1.0;
}
object Regressive extends Progression {
  val direction = -1.0;
}

trait VariableAngle {
  def at(t: Time): Angle;
}

case class ShiftingAngle(val a0: Angle, val period: Time, val direction: Progression) extends VariableAngle {

  val progression = DegreesPerSecond(direction.direction * 360.0 / period.toSeconds);

  def at(t: Time): Angle = {
    val raw = a0.toDegrees + t.toSeconds * progression.toDegreesPerSecond;
    val cropped = raw % 360.0;
    val normalised = if (cropped < 0.0) { cropped + 360.0 } else { cropped };
    Degrees(normalised)
  }
}

case class ConstantAngle(val a0: Angle) extends VariableAngle {
  def at(t: Time): Angle = a0
}

/**
 * Descriptor for an orbit
 *
 * See {@link: https://en.wikipedia.org/wiki/Orbital_elements}
 *
 * @param e Eccentricity
 * @param a Semimajor Axis
 * @param i Inclination
 * @param Omega Longitude of the ascending node
 * @param omega Argument of periapsis
 * @param M0 Mean Anomaly at epoch 0
 * @param m1 Mass of the orbiting body
 * @param centre the orbited body
 */
case class VariableOrbit(val e: Double, val a: Length, val i: Angle, val Omega: VariableAngle, val omega: VariableAngle, val M0: Angle, val m1: Mass, val centre: Orbiting, val retrograde: Boolean = false) extends Orbit {

  case class OrbitalPosition(at: Time, Omega: Angle, omega: Angle, eclipticMatrix: Matrix4, M: Angle, E: Angle, nu: Angle, pos: Vector3, posRaw: Vector3, v: Velocity) extends OrbitalSnapshot {
    def project(pos: Vector3) = {
      pos.applyMatrix4(eclipticMatrix);
    }
  }

  val mu = Constants.G * m1.toKilograms; // gravitational parameter
  val T = Seconds(2 * Math.PI * Math.sqrt(Math.pow(a.toMeters, 3) / (mu + Constants.G * centre.mass.toKilograms)));
  val n = DegreesPerSecond(360.0 / T.toSeconds);

  private val e2 = e * e;
  private val dire = 1.0 - e2;
  private val l: Length = a * dire;
  private val rap = l / (1.0 - e);
  private val rper = l / (1.0 + e)
  private val fak = Math.sqrt(dire);
  private val b = a * fak;
  private val focalDiff = a - rper;

  def orbitalPeriod = T;

  private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null, null, null, null, null, null);

  override def at(t: Time): OrbitalPosition = {
    if (!parameterCache.at.equals(t)) {
      val Omega = this.Omega.at(t);
      val omega = this.omega.at(t);
      val centrePos = centre.orbit.at(t).pos;
      val eulerMatrix = this.eulerMatrix(Omega, omega);
      val M = meanAnomaly(t);
      val E = eccentricAnomaly(M);
      val nu = trueAnomalyFromE(E);
      val rawPos = rawPositionFromE(E);
      val pos = scaledPosition(rawPos, eulerMatrix, centrePos);
      val r = radius(nu);
      val v = speed(r);
      parameterCache = OrbitalPosition(t, Omega, omega, eulerMatrix, M, E, nu, pos, rawPos, v)
    }
    return parameterCache;
  }

  private def meanAnomaly(t: Time): Angle = {
    val M = if (retrograde) {
      M0 - Degrees(n.toDegreesPerSecond * t.toSeconds);
    } else {
      M0 + Degrees(n.toDegreesPerSecond * t.toSeconds);
    }
    M.normalise()
  }

  private def radius(v: Angle): Length = {
    val ev: Double = 1.0 + e * v.cos;
    l * (1.0 / ev)
  }

  private def speed(r: Length): Velocity = {
    val v = Math.sqrt(mu * ((2.0 / r.toMeters) - (1.0 / a.toMeters)));
    MetersPerSecond(v)
  }

  private def eccentricAnomaly(M: Angle): Angle = {
    var i = 0;
    val m = M.toRadians;
    var E = if (e < 0.8) { m } else { Math.PI };
    var F = E - e * Math.sin(E) - m;

    while ((Math.abs(F) > Constants.delta) && (i < Constants.maxIter)) {
      E = E - F / (1.0 - e * Math.cos(E));
      F = E - e * Math.sin(E) - m;
      i += 1;
    }
    val Erounded = Math.round(E * Constants.accPow) / Constants.accPow;
    return Radians(Erounded).normalise();
  }

  private def trueAnomalyFromE(E: Angle): Angle = {
    val s = E.sin;
    val c = E.cos;
    val phi = Math.atan2(fak * s, c - e) / Constants.k;

    val v = Math.round(phi * Constants.accPow) / Constants.accPow;

    return Degrees(v).normalise();
  }

  private def rawPositionFromE(E: Angle): Vector3 = {
    val x = a * E.cos;
    val y = b * E.sin;
    return new Vector3(x.toKilometers - focalDiff.toKilometers, y.toKilometers, 0.0);
  }

  private def scaledPosition(posRaw: Vector3, eulerMatrix: Matrix4, centrePos: Vector3): Vector3 = {
    val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
    pos.applyMatrix4(eulerMatrix);
    pos.add(centrePos);
    return pos;
  }

  private def eulerMatrix(Omega: Angle, omega: Angle): Matrix4 = {
    val m = (new Matrix4);
    val m2 = (new Matrix4);
    m.makeRotationFromEuler(new Euler(i.toRadians, 0.0, Omega.toRadians, "ZXY"));
    m2.makeRotationFromEuler(new Euler(0.0, 0.0, omega.toRadians, "ZXY"));
    m.multiply(m2);
  };

}
