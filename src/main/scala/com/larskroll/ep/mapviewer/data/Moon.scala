package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

class Moon(name: String, id: UUID, mass: Mass, val radius: Length, val planet: Planet) extends AstronomicalBody(name, id, mass) {
    override def `type`: String = "Moon";
}