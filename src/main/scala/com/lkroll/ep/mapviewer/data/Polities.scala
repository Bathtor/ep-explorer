package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

class Polity(val designation: String, val adjective: String, val demonym: String)

object Allegiance {
  def apply(p: Polity): (String, String) = ("Allegiance" -> p.adjective)
}

object Polities {
  implicit class SinglePolityAllegiance(val polity: Polity) extends Allegiance {
    override def description = polity.adjective;
  }
  implicit class MultiPolityAllegiance(val polities: Seq[Polity]) extends Allegiance {
    override def description = polities.map { p => p.adjective }.mkString(" / ");
  }
  case class Independent(val polities: Polity*) extends Allegiance {
    override def description = if (polities.isEmpty) { "Independent" } else {
      val pS = polities.map(_.adjective).mkString("(", " / ", ")");
      s"Independent $pS"
    };
  }
  case class IndependentNamed(val name: String) extends Allegiance {
    override def description = s"Independent($name)";
  }
  case class AA(val polity: Polity) extends Allegiance {
    override def description = s"${Autonomist.designation} (${polity.adjective})";
  }
  case class CorpAsPolity(val corp: Corp) extends Polity(corp.name, corp.name, corp.name)

  case class Protectorate(val protector: Polity, val protectee: Polity) extends Allegiance {
    override def description = s"${protectee.adjective} (${protector.adjective} Protectorate)";
  }

  object IndependentPolity extends Polity("Independent", "Independent", "Independent")
  object NoPolity extends Polity("None", "None", "None")
  object Private extends Polity("Private", "Private", "Private")
  object UnkownPolity extends Polity("Unkown", "Unkown", "Unkown")
  object Contested extends IndependentNamed("Contested");

  // Major
  object Argonauts extends Polity("Argonauts", "Argonaut", "Argonaut")
  object Autonomist extends Polity("Autonomist Alliance", "Autonomist", "Autonomist")
  object Extropian extends Polity("Extropia", "Extropian", "Extropian")
  object Jovian extends Polity("Jovian Republic", "Jovian", "Jovian")
  object LunarLagrangeAlliance extends Polity("Lunar-Lagrange Alliance", "LLA", "Lunar/Langranian")
  object MorningstarConstellation extends Polity("Morningstar Constellation", "Morningstar Constellation", "Venusian")
  object PlanetaryConsortium extends Polity("Planetary Consortium", "Planetary Consortium", "Consortium")
  object Titanian extends Polity("Titanian Commonwealth", "Titanian", "Titanian")

  // Minor
  object Anarchists extends Polity("Anarchists", "Anarchist", "Anarchist")
  object Brinker extends Polity("Brinkers", "Brinker", "Brinker")
  object CarmeCompact extends Polity("Carme Compact", "Carme Compact", "Compact")
  object CatholicChurch extends Polity("Roman Catholic Church", "Catholic Church", "Catholic")
  object Europan extends Polity("Europa", "Europan", "Europan")
  object Hyperelite extends Polity("Hyperelites", "Hyperelite", "Hyperelite")
  object Israeli extends Polity("Israel", "Israeli", "Israeli")
  object Preservationists extends Polity("Preservationists", "Preservationist", "Preservationist")
  object Scum extends Polity("Scum", "Scum", "Scum")
  object Sifter extends Polity("Sifters", "Sifter", "Sifter")
  object Solarian extends Polity("Solarians", "Solarian", "Solarian")
  object TechnoCreationists extends Polity("Techno-Creationists", "Techno-Creationist", "Techno-Creationist")
  object Ultimates extends Polity("Ultimates", "Ultimate", "Ultimate")

  // Non-human
  object Factor extends Polity("The Factors", "Factor", "Factor")
  object TITANs extends Polity("The TITANs", "TITAN", "TITAN")

  // Criminals
  object Criminal extends Polity("Criminals", "Criminal", "Criminal")
  object LosZetas extends Polity("Los Zetas", "Los Zetas", "Los Zetas") // FIXME: my Spanish sucks
  object NightCartel extends Polity("Night Cartel", "Night Cartel", "Cartellian") // FIXME: not sure if the demonym makes any sense
  object NineLives extends Polity("Nine Lives", "Nine Lives", "Gangster") // FIXME: not sure if the demonym makes any sense
  object Triad extends Polity("The Triads", "Triad", "Triad")
}
