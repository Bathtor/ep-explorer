package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Planet(name: String, id: UUID, mass: Mass, val radius: Length) extends AstronomicalBody(name, id, mass) with SingleViewable {
  override def `type`: String = "Planet";
}