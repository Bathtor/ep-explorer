package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Star(name: String, id: UUID, mass: Mass, val radius: Length, val temperature: Temperature) extends AstronomicalBody(name, id, mass) {
  override def `type`: String = "Star";
}