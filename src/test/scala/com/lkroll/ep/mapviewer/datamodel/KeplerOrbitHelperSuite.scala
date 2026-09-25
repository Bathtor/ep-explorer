package com.lkroll.ep.mapviewer.datamodel

import org.scalatest.funsuite.AnyFunSuite
import squants.space.Degrees

class KeplerOrbitHelperSuite extends AnyFunSuite {
  private def tolerance(eccentricity: Double): Double =
    Constants.delta + (1.0 + eccentricity) / (2.0 * Constants.anomalyRoundingScale)

  test("a circular orbit preserves mean anomaly") {
    for (mean <- List(Degrees(0.0), Degrees(90.0), Degrees(180.0))) {
      val actual = KeplerOrbitHelper.eccentricAnomaly(mean, 0.0)
      assert(math.abs(actual.toRadians - mean.toRadians) <= tolerance(0.0))
    }
  }

  test("periapsis and apoapsis are fixed points for elliptical orbits") {
    for {
      eccentricity <- List(0.5, 0.8)
      mean <- List(Degrees(0.0), Degrees(180.0))
    } {
      val actual = KeplerOrbitHelper.eccentricAnomaly(mean, eccentricity)
      // Kepler's equation has slope at least 1 - e, so convert residual error to angle error.
      val angleTolerance = Constants.delta / (1.0 - eccentricity) + 1.0 / (2.0 * Constants.anomalyRoundingScale)
      assert(math.abs(actual.toRadians - mean.toRadians) <= angleTolerance)
    }
  }

  test("non-circular anomalies satisfy Kepler's equation in both solver branches") {
    // 0.5 and 0.8 lie on opposite sides of the solver's initial-guess boundary.
    // 30° and 210° are twelfth-turn points away from the cardinal angles.
    for {
      eccentricity <- List(0.5, 0.8)
      mean <- List(Degrees(30.0), Degrees(90.0), Degrees(210.0), Degrees(270.0))
    } {
      val eccentric = KeplerOrbitHelper.eccentricAnomaly(mean, eccentricity).toRadians
      val residual = eccentric - eccentricity * math.sin(eccentric) - mean.toRadians
      assert(math.abs(residual) <= tolerance(eccentricity))
    }
  }
}
