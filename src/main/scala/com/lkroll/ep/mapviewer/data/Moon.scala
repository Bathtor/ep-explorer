package com.lkroll.ep.mapviewer.data

import java.util.UUID;
import squants._
import squants.motion._

class Moon(_name: String, _id: UUID, _mass: Mass, val radius: Length, val planet: Planet, val ordinal: Int)
  extends AstronomicalBody(_name, _id, _mass) with SingleViewable {
  override def `type`: String = "Moon";
  def surfaceGravity: Acceleration = {
    val g = Constants.G * mass.toKilograms / Math.pow(radius.toMeters, 2);
    MetersPerSecondSquared(g)
  }
}