package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Moon(name: String, id: UUID, mass: Mass, val radius: Length, val planet: Planet, val ordinal: Int) extends AstronomicalBody(name, id, mass) with SingleViewable {
    override def `type`: String = "Moon";
}