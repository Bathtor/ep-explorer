package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._

trait StationType {
    def name: String;
}


case class ONeillCyliner(val length: Length, val radius: Length) extends StationType {
    override def name = "O'Neill Cylinder";
}

case class BernalSphere(val radius: Length) extends StationType {
    override def name = "Bernal Sphere"
}

case class ModifiedBernalSphere(val radius: Length) extends StationType {
    override def name = "Modified Bernal Sphere"
}

case object Cluster extends StationType {
    override def name = "Cluster"
}

case object UnkownStation extends StationType {
    override def name = "Unkown"
}

case class Torus(val radius: Length, val thickness: Length) extends StationType {
    override def name = "Torus"
}

case class ModifiedTorus(val radius: Length, val thickness: Length) extends StationType {
    override def name = "Modified Torus"
}

case class Asteroid(val description: String, val length: Length, val width: Length, val height: Length) extends StationType {
    override def name = description;
}

class Habitat(name: String, id: UUID, mass: Mass,
              val stationType: StationType, val centre: Orbiting,
              val allegiance: Allegiance, val langs: Seq[Language],
              val industries: Seq[Industry]) extends AstronomicalBody(name, id, mass) {
    override def `type`: String = "Habitat";
}