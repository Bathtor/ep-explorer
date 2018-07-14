package com.lkroll.ep.mapviewer.datamodel

import com.lkroll.ep.mapviewer.Main

import squants.space._
import squants.time._
import squants.motion._
//import squants.mass._

import org.denigma.threejs._

class Distance(val start: OrbitalSnapshot, val end: OrbitalSnapshot) {
  lazy val instant: Length = Kilometers(start.pos.distanceTo(end.pos) * Distance.inverseScale);
  lazy val instantLag: Time = Seconds(instant.toMeters / Constants.c.toMetersPerSecond);
}

object Distance {
  def calc(at: Time, start: Orbiting, end: Orbiting): Distance = {
    new Distance(start.orbit.at(at), end.orbit.at(at))
  }

  private val inverseScale = 1.0 / Main.scaleDistance;
}
