package com.larskroll.ep.mapviewer.data

import squants._
import squants.mass._
import squants.space._

object Yottagrams extends MassUnit with SiBaseUnit {
    val conversionFactor = MetricSystem.Yotta; // 24
    val symbol = "Yg";
}

object Zettagrams extends MassUnit with SiBaseUnit {
    val conversionFactor = MetricSystem.Zetta; // 21
    val symbol = "Zg";
}

object ExtraUnits {
    implicit class ExtDegrees(d: Angle) {
        def normalise(): Angle = {
            Degrees(normaliseDegrees(d.toDegrees))
        }
    }
    def normaliseDegrees(d: Double): Double = {
        val dnorm = d % 360.0;
        if (dnorm >= 0.0) {
            return dnorm;
        } else {
            return 360.0 - dnorm;
        }
    }
}