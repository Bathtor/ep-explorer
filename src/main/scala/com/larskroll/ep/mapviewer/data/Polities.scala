package com.larskroll.ep.mapviewer.data

class Polity(val name: String)

object Polities {
    implicit class SinglePolityAllegiance(val polity: Polity) extends Allegiance {
        override def description = polity.name;
    }
    case class Independent(val polity: Polity) extends Allegiance {
        override def description = s"Independent (${polity.name})";
    }
    object Argonauts extends Polity("Argonauts")
    object LunarLagrangeAlliance extends Polity("Lunar-Lagrange Alliance")
    object Scum extends Polity("Scum")
    object Brinker extends Polity("Brinker")
    object PlanetaryConsortium extends Polity("Planetary Consortium")
    object MorningstarConstellation extends Polity("Morningstar Constellation")
    object TITANs extends Polity("TITANs")
    object NoPolity extends Polity("None")
    object Preservationists extends Polity("Preservationists")
}