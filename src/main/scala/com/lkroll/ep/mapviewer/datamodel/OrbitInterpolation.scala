package com.lkroll.ep.mapviewer.datamodel

private[datamodel] object OrbitInterpolation {

  /** Fraction from the lower cached angle to the upper one for linear interpolation.
    *
    * Angles are in degrees in [0, 360). An upper angle below the lower angle means
    * the samples straddle 360°, so both the upper angle and a wrapped query continue
    * into the next revolution. Identical endpoints represent a single sample.
    */
  def weight(angleDegrees: Double, lowerDegrees: Double, upperDegrees: Double): Double = {
    if (lowerDegrees == upperDegrees) {
      0.0
    } else {
      val upper = if (upperDegrees < lowerDegrees) upperDegrees + 360.0 else upperDegrees
      val angle = if (angleDegrees < lowerDegrees) angleDegrees + 360.0 else angleDegrees
      (angle - lowerDegrees) / (upper - lowerDegrees)
    }
  }
}
