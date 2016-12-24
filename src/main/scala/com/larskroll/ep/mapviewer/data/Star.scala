package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Star(_name: String, _id: UUID, _mass: Mass, val radius: Length, val temperature: Temperature)
    extends AstronomicalBody(_name, _id, _mass) {
  override def `type`: String = "Star";
}
