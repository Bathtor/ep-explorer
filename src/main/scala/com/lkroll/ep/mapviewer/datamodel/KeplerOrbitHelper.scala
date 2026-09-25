package com.lkroll.ep.mapviewer.datamodel

import squants.space.{Angle, Radians}
import ExtraUnits._

private[datamodel] object KeplerOrbitHelper {
  def eccentricAnomaly(meanAnomaly: Angle, eccentricity: Double): Angle = {
    val m = meanAnomaly.toRadians
    var e = if (eccentricity < 0.8) m else Math.PI
    var residual = e - eccentricity * Math.sin(e) - m
    var iterations = 0

    while (Math.abs(residual) > Constants.delta && iterations < Constants.maxIter) {
      e -= residual / (1.0 - eccentricity * Math.cos(e))
      residual = e - eccentricity * Math.sin(e) - m
      iterations += 1
    }

    Radians(Math.round(e * Constants.anomalyRoundingScale) / Constants.anomalyRoundingScale).normalise()
  }
}
