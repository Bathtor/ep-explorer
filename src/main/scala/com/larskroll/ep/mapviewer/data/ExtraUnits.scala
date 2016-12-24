package com.larskroll.ep.mapviewer.data

import squants.{ SiBaseUnit, MetricSystem }
import squants.mass._
import squants.space._
import squants.motion._
import squants.time._
import squants.thermal._

import org.denigma.threejs.Color

object Yottagrams extends MassUnit with SiBaseUnit {
  val conversionFactor = MetricSystem.Yotta; // 24
  val symbol = "Yg";
}

object Zettagrams extends MassUnit with SiBaseUnit {
  val conversionFactor = MetricSystem.Zetta; // 21
  val symbol = "Zg";
}

object DegreesPerDay extends AngularVelocityUnit {
  val symbol = "°/d"
  val conversionFactor = (Degrees.conversionFactor * Radians.conversionFactor * Seconds.conversionFactor) / Days.conversionFactor
}

object RotationPeriod {
  def apply(period: Time): AngularVelocity = {
    DegreesPerSecond(360.0 / period.toSeconds)
  }
}

object ExtraUnits {
  implicit class ExtDegrees(val d: Angle) extends AnyVal {
    def normalise(): Angle = {
      Degrees(normaliseDegrees(d.toDegrees))
    }
  }
  implicit class ExtDoubles(val d: Double) extends AnyVal {
    def º(): Angle = Degrees(d);
    def ºd(): AngularVelocity = DegreesPerDay(d);
    def km(): Length = Kilometers(d);
    def kg(): Mass = Kilograms(d);
    def AU(): Length = AstronomicalUnits(d);
  }
  implicit class ExtTemp(val t: Temperature) extends AnyVal {
    def toRGB(): Color = {
      val temp = t.toKelvinScale / 100.0;

      val r: Double = {
        if (temp <= 66.0) {
          255.0
        } else {
          val offset = temp - 60.0;
          val red = 329.698727446 * math.pow(offset, -0.1332047592);
          clampByte(red)
        }
      };
      val g: Double = {
        if (temp <= 66.0) {
          val green = 99.4708025861 * math.log(temp) - 161.1195681661;
          clampByte(green)
        } else {
          val offset = temp - 60.0;
          val green = 288.1221695283 * math.pow(offset, -0.0755148492);
          clampByte(green)
        }
      };
      val b: Double = {
        if (temp >= 66.0) {
          255.0
        } else if (temp <= 19.0) {
          0.0
        } else {
          val offset = temp - 10.0;
          val blue = 138.5177312231 * math.log(offset) - 305.0447927307;
          clampByte(blue)
        }
      };
      val scaledR = r / 255.0;
      val scaledG = g / 255.0;
      val scaledB = b / 255.0;
      val c = new Color(scaledR, scaledG, scaledB);
      //c.setRGB(math.floor(r), math.floor(g), math.floor(b));
      //println(s"Color(r=$r=${c.r}, g=$g=${c.g}, b=$b=${c.b})=${c.getHexString()} for t=${t.toKelvinScale}")
      c
    }

    private def clampByte(d: Double): Double = {
      if (d < 0.0) { 0.0 } else if (d > 255.0) { 255.0 } else { d }
    }
  }
  def normaliseDegrees(d: Double): Double = {
    val dnorm = d % 360.0;
    if (dnorm >= 0.0) {
      return dnorm;
    } else {
      return 360.0 + dnorm;
    }
  }
}
