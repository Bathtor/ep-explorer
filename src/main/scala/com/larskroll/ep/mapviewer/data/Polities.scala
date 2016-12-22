package com.larskroll.ep.mapviewer.data

class Polity(val name: String)

object Polities {
    implicit class SinglePolityAllegiance(val polity: Polity) extends Allegiance {
        override def description = polity.name;
    }
    implicit class MultiPolityAllegiance(val polities: Seq[Polity]) extends Allegiance {
        override def description = polities.map { p => p.name }.mkString(" / ");
    }
    case class Independent(val polities: Polity*) extends Allegiance {
        override def description = s"Independent ${polities.map { p => p.name }.mkString("(", " / ", ")");}";
    }
    case class CorpAsPolity(val corp: Corp) extends Polity(corp.name)

    object Argonauts extends Polity("Argonauts")
    object Extropian extends Polity("Extropian")
    object Ultimates extends Polity("Ultimates")
    object LunarLagrangeAlliance extends Polity("Lunar-Lagrange Alliance")
    object Factor extends Polity("Factor")
    object Scum extends Polity("Scum")
    object Brinker extends Polity("Brinker")
    object TechnoCreationists extends Polity("Techno-Creationists")
    object PlanetaryConsortium extends Polity("Planetary Consortium")
    object MorningstarConstellation extends Polity("Morningstar Constellation")
    object TITANs extends Polity("TITANs")
    object Titanian extends Polity("Titanian")
    object Sifter extends Polity("Sifter")
    object Solarian extends Polity("Solarian")
    object NoPolity extends Polity("None")
    object Private extends Polity("Private")
    object Hyperelite extends Polity("Hyperelite")
    object UnkownPolity extends Polity("Unkown")
    object Israeli extends Polity("Israeli")
    object Preservationists extends Polity("Preservationists")
    object Criminal extends Polity("Criminal")
    object LosZetas extends Polity("Los Zetas")
    object Triad extends Polity("Triad")
}