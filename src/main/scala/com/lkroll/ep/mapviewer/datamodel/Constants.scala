package com.lkroll.ep.mapviewer.datamodel

import com.lkroll.ep.mapviewer.three.Matrix3

import squants.Mass;
import squants.motion._
import squants.space.Degrees;

object Constants {
  // Physical
  val G = 6.67408e-11; // gravitational constant in m^3/(kg*s^2)
  val c = MetersPerSecond(299792458); // speed of light in vacuum
  // Time
  val secondsPerDay = 86400; //24.0*60.0*60.0;
  val daysPerYear = 365.25; // approximately
  val daysPerCentury = 100.0 * daysPerYear;
  // Model calculations
  val maxIter = 30;
  val accuracy = 6.0; // 6 decimal places
  val anomalyRoundingScale = Math.pow(10.0, accuracy);
  val delta = Math.pow(10.0, -accuracy);
  val k = Degrees.conversionFactor;
  // Only Lagrange-point calculations need these Three.js matrices. Keep them lazy so
  // numerical calculations can use the constants above without constructing JS objects.
  lazy val rotate60DZ = {
    val m = new Matrix3();
    val angle = Degrees(60);
    m.set(angle.cos, -angle.sin, 0.0, angle.sin, angle.cos, 0.0, 0.0, 0.0, 1.0);
    m
  }
  lazy val rotateMinus60DZ = {
    val m = new Matrix3();
    val angle = Degrees(-60);
    m.set(angle.cos, -angle.sin, 0.0, angle.sin, angle.cos, 0.0, 0.0, 0.0, 1.0);
    m
  }
}
