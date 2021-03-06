package com.lkroll.ep.mapviewer.datamodel

import java.util.UUID;
import squants._

trait StationType {
  def name: String;
}

case class ONeillCylinder(val length: Length, val radius: Length) extends StationType {
  override def name = "O'Neill Cylinder";
}

case class HamiltonCylinder(val length: Length, val radius: Length) extends StationType {
  override def name = "Hamilton Cylinder";
}

case class BernalSphere(val radius: Length) extends StationType {
  override def name = "Bernal Sphere"
}

case class ModifiedBernalSphere(val radius: Length) extends StationType {
  override def name = "Modified Bernal Sphere"
}

case class NuestroShell(val radius: Length) extends StationType {
  override def name = "Nuestro Shell"
}

case object Cluster extends StationType {
  override def name = "Cluster"
}

case object ProcessorLocus extends StationType {
  override def name = "Processor Locus"
}

case object Swarm extends StationType {
  override def name = "Nomadic Swarm"
}

case object UnkownStation extends StationType {
  override def name = "Unkown"
}

case class UniqueStation(_name: String) extends StationType {
  override def name = s"Unique (${_name})";
}

case class Torus(val radius: Length, val thickness: Length) extends StationType {
  override def name = "Torus"
}

case class Disc(val radius: Length, val thickness: Length) extends StationType {
  override def name = "Disc"
}

case class ModifiedTorus(val radius: Length, val thickness: Length) extends StationType {
  override def name = "Modified Torus"
}

case class Asteroid(val description: String, val length: Length, val width: Length, val height: Length)
    extends StationType {
  override def name = description;
}

class Habitat(_name: String,
              _id: UUID,
              _mass: Mass,
              val stationType: StationType,
              val centre: Orbiting,
              val allegiance: Allegiance,
              val langs: Seq[Language],
              val industries: Seq[Industry])
    extends AstronomicalBody(_name, _id, _mass) {
  override def `type`: String = "Habitat";
}
