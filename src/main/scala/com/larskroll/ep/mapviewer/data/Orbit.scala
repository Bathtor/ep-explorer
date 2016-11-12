package com.larskroll.ep.mapviewer.data

//import squants._;
import squants.space._
import squants.time._
import squants.motion._
import squants.mass._

import org.denigma.threejs._

import java.util.UUID;

import com.larskroll.ep.mapviewer.Main;

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
case class ConstantOrbit(val e: Double, val a: Length, val i: Angle, val Omega: Angle, val omega: Angle, val M0: Angle, val m1: Mass, val m2: Mass) extends Orbit {

    case class OrbitalPosition(at: Time, M: Angle, E: Angle, nu: Angle, pos: Vector3, posRaw: Vector3, v: Velocity) extends OrbitalSnapshot {
        def eclipticMatrix = eulerMatrix;
        def project(pos: Vector3) = {
            pos.applyMatrix4(eulerMatrix);            
        }
    }

    val mu = Constants.G * m1.toKilograms; // gravitational parameter
    val T = Seconds(2 * Math.PI * Math.sqrt(Math.pow(a.toMeters, 3) / (mu + Constants.G * m2.toKilograms)));
    val n = DegreesPerSecond(360.0 / T.toSeconds);

    private lazy val eulerMatrix = (new Matrix4)
        .set(Omega.cos * omega.cos - Omega.sin * i.cos * omega.sin,
            Omega.sin * omega.cos + Omega.cos * i.cos * omega.sin,
            i.sin * omega.sin,
            0.0,
            -Omega.cos * omega.sin - Omega.sin * i.cos * omega.cos,
            -Omega.sin * omega.sin + Omega.cos * i.cos * omega.cos,
            i.sin * omega.cos,
            0.0,
            i.sin * Omega.sin,
            -i.sin * Omega.cos,
            i.cos,
            0.0,
            0.0, 0.0, 0.0, 0.1);

    private val e2 = e * e;
    private val a1e2: Length = a * (1.0 - e2);
    private val fak = Math.sqrt(1.0 - e2);
    private val b = a * fak;

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
        val M = M0 + Degrees(n.toDegreesPerSecond * t.toSeconds);
        M.normalise()
    }

    private def radius(nu: Angle): Length = {
        val ev: Double = 1.0 + e * nu.cos;
        a1e2 * (1.0 / ev)
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
        val x = a * (E.cos - e);
        val y = b * E.sin;
        return new Vector3(x.toKilometers, y.toKilometers, 0.0);
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
case class VariableOrbit(val e: Double, val a: Length, val i: Angle, val Omega: VariableAngle, val omega: VariableAngle, val M0: Angle, val m1: Mass, val centre: Orbiting) extends Orbit {

    case class OrbitalPosition(at: Time, Omega: Angle, omega: Angle, eclipticMatrix: Matrix4, M: Angle, E: Angle, nu: Angle, pos: Vector3, posRaw: Vector3, v: Velocity) extends OrbitalSnapshot {
        def project(pos: Vector3) = {
            pos.applyMatrix4(eclipticMatrix);            
        }
    }

    val mu = Constants.G * m1.toKilograms; // gravitational parameter
    val T = Seconds(2 * Math.PI * Math.sqrt(Math.pow(a.toMeters, 3) / (mu + Constants.G * centre.mass.toKilograms)));
    val n = DegreesPerSecond(360.0 / T.toSeconds);

    val e2 = e * e;
    val a1e2: Length = a * (1.0 - e2);
    val fak = Math.sqrt(1.0 - e2);
    val b = a * fak;

    def orbitalPeriod = T;

    private var parameterCache: OrbitalPosition = OrbitalPosition(Seconds(Double.NaN), null, null, null, null, null, null, null, null, null);

    override def at(t: Time): OrbitalPosition = {
        if (!parameterCache.at.equals(t)) {
            val Omega = this.Omega.at(t);
            val omega = this.omega.at(t);
            val centrePos = centre.orbit.at(t).pos;
            val eulerMatrix = this.eulerMatrix(Omega, omega, centrePos);
            val M = meanAnomaly(t);
            val E = eccentricAnomaly(M);
            val nu = trueAnomalyFromE(E);
            val rawPos = rawPositionFromE(E);
            val pos = scaledPosition(rawPos, eulerMatrix);
            val r = radius(nu);
            val v = speed(r);
            parameterCache = OrbitalPosition(t, Omega, omega, eulerMatrix, M, E, nu, pos, rawPos, v)
        }
        return parameterCache;
    }

    private def meanAnomaly(t: Time): Angle = {
        val M = M0 + Degrees(n.toDegreesPerSecond * t.toSeconds);
        M.normalise()
    }

    private def radius(v: Angle): Length = {
        val ev: Double = 1.0 + e * v.cos;
        a1e2 * (1.0 / ev)
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
        val x = a * (E.cos - e);
        val y = b * E.sin;
        return new Vector3(x.toKilometers, y.toKilometers, 0.0);
    }

    private def scaledPosition(posRaw: Vector3, eulerMatrix: Matrix4): Vector3 = {
        val pos = new Vector3(posRaw.x * Main.scaleDistance, posRaw.y * Main.scaleDistance, posRaw.z);
        pos.applyMatrix4(eulerMatrix);
        return pos;
    }

    private def eulerMatrix(Omega: Angle, omega: Angle, centrePos: Vector3): Matrix4 = {
        val mat = (new Matrix4)
            .set(Omega.cos * omega.cos - Omega.sin * i.cos * omega.sin,
                Omega.sin * omega.cos + Omega.cos * i.cos * omega.sin,
                i.sin * omega.sin,
                centrePos.x,
                -Omega.cos * omega.sin - Omega.sin * i.cos * omega.cos,
                -Omega.sin * omega.sin + Omega.cos * i.cos * omega.cos,
                i.sin * omega.cos,
                centrePos.y,
                i.sin * Omega.sin,
                -i.sin * Omega.cos,
                i.cos,
                centrePos.z,
                0, 0, 0, 1);
        //        val mat = (new Matrix4);
        //        mat.identity();
        //        mat.setPosition(centrePos);
        return mat;
    };

}