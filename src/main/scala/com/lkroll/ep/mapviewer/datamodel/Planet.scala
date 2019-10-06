package com.lkroll.ep.mapviewer.datamodel

import java.util.UUID;
import squants._
import squants.motion._

class Planet(_name: String, _id: UUID, _mass: Mass, val radius: Length)
    extends AstronomicalBody(_name, _id, _mass)
    with SingleViewable {
  override def `type`: String = "Planet";
  def surfaceGravity: Acceleration = {
    val g = Constants.G * mass.toKilograms / Math.pow(radius.toMeters, 2);
    MetersPerSecondSquared(g)
  }
}
