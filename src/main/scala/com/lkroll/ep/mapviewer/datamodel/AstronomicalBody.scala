package com.lkroll.ep.mapviewer.datamodel

import java.util.UUID;
import squants._

abstract class AstronomicalBody(_name: String, _id: UUID, val mass: Mass) extends AstronomicalObject(_name, _id) {}
