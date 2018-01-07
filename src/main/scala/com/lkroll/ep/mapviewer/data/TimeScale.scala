package com.lkroll.ep.mapviewer.data

import squants.time._

/*
 * Adapted from http://www.giss.nasa.gov/tools/mars24/help/algorithm.html
 */

final class TimeScale(val t: Time, val standard: TimeStandard) {
  override def toString(): String = {
    val ts = t.toString();
    s"$ts ${standard.symbol}"
  }

  def primaryStandard = JulianDateTT;

  def to(target: TimeStandard): Time = {
    val jdtt = standard.toJulianDateTT(t);
    target.fromJulianDateTT(jdtt);
  }

  def toJDTT = to(JulianDateTT);

  def +(that: TimeScale): TimeScale = {
    if (this.standard.equals(that.standard)) {
      TimeScale((this.t + that.t), this.standard)
    } else {
      val thisPrimary = this.toJDTT;
      val thatPrimary = that.toJDTT;
      JulianDateTT(thisPrimary + thatPrimary)
    }
  }

  def -(that: TimeScale): TimeScale = {
    if (this.standard.equals(that.standard)) {
      TimeScale((this.t - that.t), this.standard)
    } else {
      val thisPrimary = this.toJDTT;
      val thatPrimary = that.toJDTT;
      JulianDateTT(thisPrimary - thatPrimary)
    }
  }

}

object TimeScale {
  private[data] def apply(n: Time, standard: TimeStandard) = new TimeScale(n, standard);
}

object Years extends TimeUnit {
  val conversionFactor = Days.conversionFactor * Constants.daysPerYear
  val symbol = "year"
}

object TimeUtils {
  def toFull(unit: TimeUnit)(t: Time): (Long, Time) = {
    val tu = t.to(unit);
    val tuFull = Math.floor(tu);
    val rest = tu - tuFull;
    val restT = unit.apply(rest);
    (tuFull.toLong, restT)
  }
}

trait TimeStandard {
  def apply[A](n: A)(implicit num: Numeric[A]) = TimeScale(primaryUnit(n), this);
  def apply(t: Time) = TimeScale(t, this);
  def symbol: String;
  def primaryUnit: TimeUnit;
  def epoch: TimeScale;
  def toJulianDateTT(t: Time): Time;
  def fromJulianDateTT(t: Time): Time;
}

object UnixTime extends TimeStandard {
  override def symbol: String = "UTC";
  override def primaryUnit: TimeUnit = Seconds;
  override def epoch: TimeScale = JulianDateTT(2440587.5); // 0h Jan 1, 1970
  def toJulianDateTT(t: Time): Time = {
    val jdUT = epoch.t + t;
    val jdTT = jdUT + leapSecondsAdjustment(jdUT);
    jdTT
  }
  def fromJulianDateTT(t: Time): Time = {
    val jdUT = t - leapSecondsAdjustment(t);
    val ut = jdUT - epoch.t;
    ut
  }

  private def leapSecondsAdjustment(jdUT: Time): Time = {
    val T = centuriesSinceJ2000(jdUT);
    Seconds(64.184 + 59 * T - 51.2 * Math.pow(T, 2) - 67.1 * Math.pow(T, 3) - 16.4 * Math.pow(T, 4))
  }

  private def centuriesSinceJ2000(jdUT: Time): Double = {
    (jdUT - J2000TT.epoch.t).toDays / Constants.daysPerCentury;
  }
}

object JulianDateTT extends TimeStandard {
  override def symbol: String = "JD(TT)";
  override def primaryUnit: TimeUnit = Days;
  override def epoch: TimeScale = JulianDateTT(0.0); // reference epoch
  def toJulianDateTT(t: Time): Time = t;
  def fromJulianDateTT(t: Time): Time = t;
}

object J2000TT extends TimeStandard {
  override def symbol: String = "J2000(TT)";
  override def primaryUnit: TimeUnit = Days;
  override def epoch: TimeScale = JulianDateTT(2451545.0); // January 1, 2000, 11:58:55.816 UTC
  def toJulianDateTT(t: Time): Time = t + epoch.t;
  def fromJulianDateTT(t: Time): Time = t - epoch.t;
}

object JulianDateTAI extends TimeStandard {
  override def symbol: String = "JD(TAI)";
  override def primaryUnit: TimeUnit = Days;
  override def epoch: TimeScale = JulianDateTT(Seconds(-32.184));
  def toJulianDateTT(t: Time): Time = t + epoch.t;
  def fromJulianDateTT(t: Time): Time = t - epoch.t;
}

object AFTT extends TimeStandard {
  override def symbol: String = "AF(TT)";
  override def primaryUnit: TimeUnit = Days;
  override def epoch: TimeScale = JulianDateTT(2500123.25); // January 1, 2133 UTC
  def toJulianDateTT(t: Time): Time = t + epoch.t;
  def fromJulianDateTT(t: Time): Time = t - epoch.t;
}

// WRONG^^
//object ADTT extends TimeStandard {
//    override def symbol: String = "AD(TT)";
//    override def primaryUnit: TimeUnit = Days;
//    override def epoch: TimeScale = JulianDateTT(1721424.5); // January 1st 00:00 0AD
//    def toJulianDateTT(t: Time): Time = t + epoch.t;
//    def fromJulianDateTT(t: Time): Time = t - epoch.t;
//}
//
//object BC {
//
//    def apply(t: Time, year: Int): TimeScale = {
//        val yearDays = year * Constants.daysPerYear;
//        val t0AD = ADTT(t);
//        val tJD = t0AD.toJDTT;
//        val dJD = tJD.toDays;
//        val adjustedDate = dJD - (yearDays + 1.0); // since BC years are negative to AD years
//        JulianDateTT(adjustedDate)
//    }
//}

