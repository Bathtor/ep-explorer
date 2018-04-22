package com.lkroll.ep.mapviewer.datamodel

import squants.Mass;
import squants.space.Degrees;

import org.denigma.threejs.Matrix3

object Constants {
  // Physical
  val G = 6.67408e-11; // gravitational constant in m^3/(kg*s^2)
  // Time
  val secondsPerDay = 86400; //24.0*60.0*60.0;
  val daysPerYear = 365.25; // approximately
  val daysPerCentury = 100.0 * daysPerYear;
  // Model calculations
  val maxIter = 30;
  val accuracy = 6.0; // 6 decimal places
  val accPow = Math.pow(10.0, accuracy);
  val delta = Math.pow(10.0, -accuracy);
  val k = Degrees.conversionFactor;
  val rotate60DZ = {
    val m = new Matrix3();
    val angle = Degrees(60);
    m.set(angle.cos, -angle.sin, 0.0,
      angle.sin, angle.cos, 0.0,
      0.0, 0.0, 1.0);
    m
  }
  val rotateMinus60DZ = {
    val m = new Matrix3();
    val angle = Degrees(-60);
    m.set(angle.cos, -angle.sin, 0.0,
      angle.sin, angle.cos, 0.0,
      0.0, 0.0, 1.0);
    m
  }
}
