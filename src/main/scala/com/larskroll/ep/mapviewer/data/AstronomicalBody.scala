package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

abstract class AstronomicalBody(name: String, id: UUID, val mass: Mass) extends AstronomicalObject(name, id) {
  
}