package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Planet(_name: String, _id: UUID, _mass: Mass, val radius: Length)
    extends AstronomicalBody(_name, _id, _mass) with SingleViewable {
  override def `type`: String = "Planet";
}
