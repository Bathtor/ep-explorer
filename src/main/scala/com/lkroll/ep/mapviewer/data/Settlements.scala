package com.lkroll.ep.mapviewer.data

import squants.space._
import java.util.UUID

object Settlements {
  import Geography._
  import Languages._
  import Polities.{ PlanetaryConsortium => _, _ }
  import Industries._
  import Corps._
  import ExtraUnits._

  object Earth {

    object Greenwich extends Settlement("Greenwich", UUID.randomUUID(),
      (North(51, 28, 40), West(0, 0, 5)), Planets.Earth, Kilometers(10),
      AbandonedToTITANs, Seq(English), Seq.empty[Industry])

    object KilimanjaroSE extends Settlement("Kilimanjaro Space Elevator", UUID.randomUUID(),
      (South(3, 4, 33), East(37, 21, 12)), Planets.Earth, Kilometers(48),
      AbandonedToTITANs, Seq(English, Swahili), Seq.empty[Industry])

    object KilimanjaroSEOrbital extends SyncOrbitStation("Kilimanjaro Orbital Station", UUID.randomUUID(),
      (South(3, 4, 33), East(37, 21, 12)), Kilometers(35786.0), Planets.Earth,
      AbandonedToTITANs, Seq(English, Swahili), Seq.empty[Industry])

    object NewYorkCity extends Settlement("New York City Metroplex", UUID.randomUUID(),
      (North(40, 42, 46), West(74, 0, 21)), Planets.Earth, Kilometers(150),
      AbandonedToTITANs, Seq(English), Seq.empty[Industry])

    object Bejing extends Settlement("Bejing", UUID.randomUUID(),
      (North(39, 55, 0), East(116, 23, 0)), Planets.Earth, Kilometers(72),
      AbandonedToTITANs, Seq(Mandarin), Seq.empty[Industry])

    object PanamaCity extends Settlement("Panama City", UUID.randomUUID(),
      (North(8, 59, 0), West(79, 39, 0)), Planets.Earth, Kilometers(35),
      AbandonedToTITANs, Seq(Spanish), Seq.empty[Industry])

    object Tashkent extends Settlement("Tashkent", UUID.randomUUID(),
      (North(41, 16, 0), East(69, 13, 0)), Planets.Earth, Kilometers(10),
      TITANs, Seq(Russian), Seq.empty[Industry])

    object Moscow extends Settlement("Moscow", UUID.randomUUID(),
      (North(55, 45, 0), East(37, 37, 0)), Planets.Earth, Kilometers(35),
      TITANs, Seq(Russian), Seq.empty[Industry])

    object Hawaii extends Settlement("Hawaii (Mauna Kea)", UUID.randomUUID(),
      (North(19, 49, 14), West(155, 28, 5)), Planets.Earth, Kilometers(50),
      TITANs, Seq(English), Seq.empty[Industry])

    object RioDeJaneiro extends Settlement("Rio de Janeiro", UUID.randomUUID(),
      (South(22, 54, 30), West(43, 11, 47)), Planets.Earth, Kilometers(45),
      AbandonedToTITANs, Seq(Portuguese), Seq.empty[Industry])

    object CapeTown extends Settlement("Cape Town", UUID.randomUUID(),
      (South(33, 55, 31), East(18, 25, 26)), Planets.Earth, Kilometers(30),
      AbandonedToTITANs, Seq(Portuguese), Seq.empty[Industry])
  }
  import Earth._

  object Luna {
    //        object ZeroMeridian extends Settlement("Zero Meridian", UUID.randomUUID(),
    //            (North(0, 0, 0), West(0, 0, 0)), Moons.Luna, Kilometers(10),
    //            NoPolity, Seq.empty[Language], Seq.empty[Industry])
    object Erato extends Settlement("Erato", UUID.randomUUID(),
      (North(14, 47, 0), West(11, 32, 0)), Moons.Luna, Kilometers(10),
      LunarLagrangeAlliance, Seq(Mandarin, English), Seq(Fashion, Finance)) {
      override def extraInfo = Seq(("Population" -> "5 million+"));
    }
    object Nectar extends Settlement("Nectar", UUID.randomUUID(),
      (South(15, 2, 0), East(34, 6, 0)), Moons.Luna, Kilometers(10),
      Polities.PlanetaryConsortium, Seq(English, Spanish), Seq(Fashion, Finance)) {
      override def extraInfo = Seq(("Population" -> "5 million+"));
    }
    object Shackle extends Settlement("Shackle", UUID.randomUUID(),
      (South(89, 9, 0), East(0, 0, 0)), Moons.Luna, Kilometers(10),
      LunarLagrangeAlliance, Seq(English, Hindi), Seq(WaterExtraction, Mining)) {
      override def extraInfo = Seq(("Population" -> "5 million+"));
    }
    object CleverHands extends Settlement("Clever Hands", UUID.randomUUID(),
      (North(0, 24, 0), West(74, 30, 0)), Moons.Luna, Kilometers(5),
      PlanetaryConsortium(Somatek), Seq(English), Seq(Research, GeneticalEngineering, Uplift)) {
      override def extraInfo = Seq(("Population" -> "60 000"));
    }
    object TheColony extends Settlement("The Colony/Karpola", UUID.randomUUID(),
      (South(3, 12, 0), East(79, 43, 0)), Moons.Luna, Kilometers(5),
      LunarLagrangeAlliance, Seq(Javanese), Seq()) {
    }
    object Feynman extends Settlement("Feynman", UUID.randomUUID(),
      (North(38, 47, 0), West(152, 23, 0)), Moons.Luna, Kilometers(5),
      LunarLagrangeAlliance, Seq(Arabic, English), Seq(NanoTechnology, Research)) {
      override def extraInfo = Seq(("Population" -> "50 000"), ("Owner" -> Omnicor.name));
    }
    object Muir extends Settlement("Muir", UUID.randomUUID(),
      (North(34, 27, 0), West(52, 37, 0)), Moons.Luna, Kilometers(5),
      Independent(Preservationists), Seq(English, German), Seq(EcosystemManagement)) {
    }
    object NewMumbai extends Settlement("New Mumbai (Containment Zone))", UUID.randomUUID(),
      (North(27, 9, 0), East(147, 3, 0)), Moons.Luna, Kilometers(15),
      AbandonedToTITANs, Seq(English, Hindi), Seq()) {
    }
  }
  import Luna._

  object Mars {
    object OlympusCity extends Settlement("Olympus City/Space Elevator", UUID.randomUUID(),
      (North(18, 35, 0), West(134, 48, 0)), Planets.Mars, Kilometers(40),
      PlanetaryConsortium(OIA), Seq(Mandarin, English), Seq(Transport, Shipping, Trade)) {
      override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Tether extends SyncOrbitStation("Tether (Asteroid)", UUID.randomUUID(),
      (North(18, 35, 0), West(134, 48, 0)), Kilometers(23000.0), Planets.Mars,
      PlanetaryConsortium(OIA), Seq(Mandarin, English), Seq.empty[Industry])

    object VallesNewShanghai extends Settlement("Valles-New Shanghai", UUID.randomUUID(),
      (South(12, 8, 0), West(49, 4, 0)), Planets.Mars, Kilometers(80),
      Polities.PlanetaryConsortium, Seq(English, Mandarin, Wu, Arabic, Hindi, Urdu, Portuguese),
      Seq(Trade, Politics, Research, Microfacturing, HeavyIndustry, Entertainment)) {
      override def extraInfo = Seq(("Population" -> "37 million"));
    }
    object NoctisQianjiao extends Settlement("Noctis-Qianjiao", UUID.randomUUID(),
      (South(7, 0, 0), West(102, 12, 0)), Planets.Mars, Kilometers(50),
      Polities.PlanetaryConsortium, Seq(English, Hindi, Indonesian, Mandarin, German, Dutch),
      Seq(Transport, Design, AI, Robotics, Microfacturing, Biodesign, Terraforming, Research)) {
      override def extraInfo = Seq(("Population" -> "13 million"));
    }
    object Elysium extends Settlement("Elysium", UUID.randomUUID(),
      (North(22, 0, 0), East(141, 13, 0)), Planets.Mars, Kilometers(10),
      PlanetaryConsortium(Experia), Seq(Hindi, English, Cantonese, Mandarin, Indonesian),
      Seq(Entertainment)) {
      override def extraInfo = Seq(("Population" -> "9 million"));
    }
    object Ashoka extends Settlement("Ashoka", UUID.randomUUID(),
      (North(10, 24, 0), West(25, 48, 0)), Planets.Mars, Kilometers(5),
      PlanetaryConsortium(TTO), Seq(Japanese, Korean, Mandarin, Arabic, English),
      Seq(Terraforming, Tourism)) {
      override def extraInfo = Seq(("Population" -> "10 000"));
    }
    object NewDazhai extends Settlement("New Dazhai", UUID.randomUUID(),
      (South(36, 0, 0), West(44, 0, 0)), Planets.Mars, Kilometers(5),
      PlanetaryConsortium(FaJing), Seq(Mandarin),
      Seq(Mining, Terraforming)) {
      override def extraInfo = Seq(("Population" -> "350 000"));
    }
    object PilsenerCity extends Settlement("Pilsener City", UUID.randomUUID(),
      (South(12, 3, 0), West(74, 45, 0)), Planets.Mars, Kilometers(5),
      PlanetaryConsortium(FaJing), Seq(Japanese),
      Seq(Agriculture, Brewing)) {
      //override def extraInfo = Seq(("Population" -> "350 000"));
    }
    object PathfinderGate extends PandoraGate("Pathfinder Gate", UUID.randomUUID(),
      (South(18, 34, 0), West(175, 14, 0)), Planets.Mars,
      PlanetaryConsortium(Pathfinder))

    object PathfinderCity extends Settlement("Pathfinder City", UUID.randomUUID(),
      (South(15, 3, 0), West(175, 10, 0)), Planets.Mars, Kilometers(5),
      PlanetaryConsortium(Pathfinder), Seq(English, Mandarin),
      Seq(Gatecrashing, Colonization)) {
      //override def extraInfo = Seq(("Population" -> "350 000"));
    }
    object Anyang extends Settlement("Anyang", UUID.randomUUID(),
      (South(45, 25, 0), East(78, 30, 0)), Planets.Mars, Kilometers(5),
      Polities.PlanetaryConsortium, Seq(Mandarin, Cantonese, Korean, English),
      Seq(Terraforming, Research, GeneticalEngineering, Bioengineering)) {
      override def extraInfo = Seq(("Population" -> "300 000"));
    }
    object Qurain extends Settlement("Qurain", UUID.randomUUID(),
      (South(15, 3, 0), West(179, 45, 0)), Planets.Mars, Kilometers(5),
      AbandonedToTITANs, Seq(Arabic),
      Seq()) {
      //override def extraInfo = Seq(("Population" -> "350 000"));
    }
    object Kartika extends Settlement("Kartika", UUID.randomUUID(),
      (South(8, 5, 0), West(141, 44, 0)), Planets.Mars, Kilometers(5),
      AbandonedToTITANs, Seq(),
      Seq()) {
      //override def extraInfo = Seq(("Population" -> "350 000"));
    }
  }
  import Mars._

  object Venus {
    /* Note that pretty much all aerostats just drift with the winds,
        * but this is very difficult to calculate,
        * so they'll just be drawn as if stationary for now
   		*/
    object Octavia extends Aerostat("Octavia", UUID.randomUUID(),
      (North(12, 9, 0), East(123, 3, 0)), 54.0.km, Planets.Venus,
      MorningstarConstellation, Seq(Cantonese, English, French),
      Seq(Trade, Shipping, Tourism, Politics, Terraforming)) {
      override def extraInfo = Seq(
        ("Population" -> "500 000"),
        ("Mayor" -> "Halis Sapien"));
    }
    object AphroditePrime extends Aerostat("Aphrodite Prime", UUID.randomUUID(),
      (North(25, 9, 0), East(187, 0, 0)), 53.0.km, Planets.Venus,
      MorningstarConstellation, Seq(English, Mandarin, Spanish),
      Seq(Bioengineering, Biodesign, Tourism, MorphProduction)) {
      override def extraInfo = Seq(
        ("Population" -> "300 000"));
    }
    object Lucifer extends Aerostat("Lucifer", UUID.randomUUID(),
      (North(45, 0, 0), East(12, 0, 0)), 55.0.km, Planets.Venus,
      MorningstarConstellation, Seq(Arabic, English, French, Russian),
      Seq(Mining)) {
      override def extraInfo = Seq(
        ("Population" -> "230 000"));
    }
    object TheShack extends Aerostat("The Shack", UUID.randomUUID(),
      (North(3, 0, 0), West(36, 0, 0)), 53.0.km, Planets.Venus,
      MorningstarConstellation, Seq(Hindi, Portuguese, Wu),
      Seq(AerialConstruction)) {
      override def extraInfo = Seq(
        ("Population" -> "130 000"),
        ("Mayor" -> "Colin Sandric"));
    }
    object Parvarti extends Aerostat("Parvarti", UUID.randomUUID(),
      (South(9, 0, 0), West(79, 0, 0)), 54.0.km, Planets.Venus,
      Independent(NoPolity), Seq(English, Farsi, Japanese),
      Seq(Entertainment, Tourism)) {
      override def extraInfo = Seq(
        ("Population" -> "130 000"),
        ("Mayor" -> "Colin Sandric"));
    }
    object Shukra extends Aerostat("Shukra", UUID.randomUUID(),
      (South(21, 0, 0), West(132, 0, 0)), 53.0.km, Planets.Venus,
      MorningstarConstellation, Seq(English, Mandarin),
      Seq(Nanofabrication, SoftwareDesign)) {
    }
    object Etemenanki extends Aerostat("Etemenanki", UUID.randomUUID(),
      (South(45, 0, 0), West(98, 0, 0)), 52.0.km, Planets.Venus,
      Polities.PlanetaryConsortium, Seq(English, Mandarin, Arabic),
      Seq()) {
    }
    object DeepReach extends Aerostat("Deep Reach", UUID.randomUUID(),
      (South(32, 0, 0), West(176, 0, 0)), 20.0.km, Planets.Venus,
      PlanetaryConsortium(Omnicor), Seq(Japanese, Russian),
      Seq(Chemistry, Research)) {
    }
  }
  import Venus._

  object Mercury {
    object AlHamadhanj extends Settlement("Al-Hamadhanj", UUID.randomUUID(),
      (North(38, 49, 0), West(89, 43, 0)), Planets.Mercury, Kilometers(20),
      Independent(Sifter), Seq(Arabic, Hindi), Seq(Trade, Mining, Politics)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Caloris18 extends Settlement("Caloris 18 (Quarantined)", UUID.randomUUID(),
      (North(10, 0, 0), West(190, 0, 0)), Planets.Mercury, Kilometers(5),
      TITANs, Seq(), Seq()) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Cannon extends Settlement("Cannon", UUID.randomUUID(),
      (North(7, 0, 0), West(167, 0, 0)), Planets.Mercury, Kilometers(2),
      Hypercorp(JaehonOffworld), Seq(Korean), Seq(Shipping, Mining)) {
      override def extraInfo = Seq(
        ("Population" -> "10 000"),
        ("Note" -> "Cannon is a moving city-sized mass driver."));
    }
    object DelacroixShelley extends Settlement("Delacroix-Shelley", UUID.randomUUID(),
      (South(44, 46, 0), West(129, 0, 0)), Planets.Mercury, Kilometers(10),
      Hypercorp(FaJing), Seq(English, Mandarin), Seq(Military, Research)) {
    }
    object Lumina extends Settlement("Lumina", UUID.randomUUID(),
      (North(82, 0, 0), West(11, 0, 0)), Planets.Mercury, Kilometers(10),
      Hypercorp(MultipleCorps), Seq(English, Mandarin, Arabic), Seq(PowerGeneration, Research, Tourism)) {
    }
  }
  import Mercury._;

  object Ceres {
    object Aventine extends Settlement("Aventine", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), Planets.Ceres, Kilometers(5), // no idea where exactly it is
      Extropian, Seq(English, Portuguese, Spanish, Tagalog),
      Seq(Transport, Shipping)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Wujec extends Bathyscaphe("Wujec", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), 100.0.km, Planets.Ceres, // no idea where exactly it is
      Extropian, Seq(English, Portuguese, Spanish, Tagalog),
      Seq(Trade, Bioengineering)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Piazzi {
      val name = "Piazzi";
      val planet = Planets.Ceres;
      val allegiance: Allegiance = Extropian;
      val langs = Seq(English, Portuguese, Spanish, Tagalog);
      val industries = Seq(Trade, Shipping, Smuggling);

      val atmos = new SyncOrbitStation(name, UUID.randomUUID(),
        (North(0, 0, 0), West(0, 0, 0)), (1192.240.km - Planets.Ceres.radius), Planets.Ceres, // no idea where exactly it is
        allegiance, langs, industries);
      object Hab extends Habitat(name, UUID.randomUUID(), squants.Kilograms(2.4e10),
        Torus(0.845.km, 0.065.km), planet,
        allegiance, langs, industries) with Orbiting {
        val orbit = new ConstantOrbit(0.0, 1192.240.km, 149.0.º, 291.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less this^^
      }
    }

    object Proserpina extends Bathyscaphe("Proserpina", UUID.randomUUID(),
      (North(45, 0, 0), West(76, 0, 0)), 105.0.km, Planets.Ceres, // no idea where exactly it is
      PlanetaryConsortium(Prosperity), Seq(English, Mandarin),
      Seq(FoodIndustry, Drugs)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
  }
  import Ceres._

  object Ganymede {
    object LibertyCity extends UndergroundSettlement("Liberty City", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), 0.25.km, Moons.Ganymede, // no idea where exactly it is
      Jovian, Seq(English, Spanish),
      Seq(FoodIndustry, Microfacturing, MilitaryTech, Politics)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object LibertyStation {
      val name = "Liberty Station";
      val moon = Moons.Ganymede;
      val allegiance: Allegiance = Jovian;
      val langs = Seq(English, Spanish);
      val industries = Seq(Trade, Shipping, AerospaceEngineering, ZeroGManufacturing, Military);

      val atmos = new SyncOrbitStation(name, UUID.randomUUID(),
        (North(0, 0, 0), West(0, 0, 0)), (45745.871.km - Moons.Ganymede.radius), moon, // no idea where exactly it is
        allegiance, langs, industries);
      object Hab extends Habitat(name, UUID.randomUUID(), squants.Kilograms(2.4e10),
        Asteroid("Cluster/Beehive", 8.0.km, 4.2.km, 3.8.km), moon,
        allegiance, langs, industries) with Orbiting {
        val orbit = new ConstantOrbit(0.0, 45745.871.km, 334.57.º, 268.20.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less this^^
      }
    }
  }
  import Ganymede._

  object Io {
    object MauiPatera extends UndergroundSettlement("Maui Patera Rehabilitation Center", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), 0.25.km, Moons.Io, // no idea where exactly it is
      Jovian, Seq(English, Spanish),
      Seq(Mining, Prison)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
  }
  import Io._

  object Callisto {
    object Hyoden extends UndergroundSettlement("Hyoden", UUID.randomUUID(),
      (North(43, 0, 0), West(107, 0, 0)), 0.25.km, Moons.Callisto, // no idea where exactly it is
      Independent(), Seq(Japanese, Indonesian, English),
      Seq(Mining, MilitaryTech, Research)) {
      override def extraInfo = Seq(("Population" -> "2 million"));
    }
    object Gerdr extends UndergroundSettlement("Gerðr", UUID.randomUUID(),
      (South(2, 20, 0), West(355, 30, 0)), 0.25.km, Moons.Callisto, // no idea where exactly it is
      Protectorate(Jovian, IndependentPolity), Seq(Skandinaviska, German, English),
      Seq(Mining)) {
      //override def extraInfo = Seq(("Population" -> "2 million"));
    }
  }
  import Callisto._

  object Europa {
    object ConamaraChaos extends Settlement("Conamara Chaos elevator head", UUID.randomUUID(),
      (North(9, 42, 0), East(87, 20, 0)), Moons.Europa, Kilometers(5),
      Europan, Seq(Russian, Japanese, Spanish, Indonesian),
      Seq(Transport, Shipping)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Conamara extends Bathyscaphe("Conamara", UUID.randomUUID(),
      (North(9, 42, 0), East(87, 20, 0)), 30.0.km, Moons.Europa,
      Europan, Seq(Russian, Japanese, Spanish, Indonesian),
      Seq(Trade, Bioengineering, Research)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object Pwyll extends Settlement("Pwyll elevator head", UUID.randomUUID(),
      (South(25, 15, 0), East(88, 36, 0)), Moons.Europa, Kilometers(5),
      Europan, Seq(Russian, Japanese, Spanish, Indonesian),
      Seq(Transport, Shipping)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
    object TheNorns extends Bathyscaphe("The Norns", UUID.randomUUID(),
      (South(25, 15, 0), East(88, 36, 0)), 28.0.km, Moons.Europa,
      Europan, Seq(Russian, Japanese, Spanish, Indonesian),
      Seq(Research, Exploration)) {
      //override def extraInfo = Seq(("Population" -> "1 million"));
    }
  }
  import Europa._;

  object SaturnSystem {
    object Thoroughgood extends Settlement("Thoroughgood", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), Moons.Dione, Kilometers(5), // no idea where
      Autonomist, Seq(English, Korean, Mandarin),
      Seq(ComSystems, SignalAnalysis, Hacktivism, Information)) {
      override def extraInfo = Seq(("Population" -> "350 000"));
    }
    object Profunda extends Settlement("Profunda", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), Moons.Enceladus, Kilometers(30), // no idea where
      AA(Extropian), Seq(English, Javanese, Tamil),
      Seq(Mining)) {
      override def extraInfo = Seq(("Population" -> "850 000"));
    }
    object HarmoniusAnarchy extends Settlement("Harmonius Anarchy", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), Moons.Mimas, Kilometers(5), // no idea where
      Autonomist, Seq(Cantonese, Mandarin),
      Seq(ZeroGManufacturing, SoftwareDesign, Microfacturing)) {
      override def extraInfo = Seq(("Population" -> "500 000"));
    }
    object Godwinhead extends Settlement("Godwinhead", UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), Moons.Tethys, Kilometers(5), // no idea where
      AA(Anarchists), Seq(English, Hindi, Punjabi),
      Seq(Art, Bioengineering, Research, Tourism)) {
      override def extraInfo = Seq(("Population" -> "200 000"));
    }
  }
  import SaturnSystem._;

  object Titan {
    object Nyhavn extends Settlement("Nyhavn", UUID.randomUUID(),
      (South(7, 0, 0), West(122, 0, 0)), Moons.Titan, 25.km,
      Titanian, Seq(Skandinaviska, English, French),
      Seq(Art, Entertainment, Politics, Research, Trade, Transport)) {
      override def extraInfo = Seq(("Population" -> "12million+"), ("Notes" -> "Capital of the Commonwealth"));
    }
    object Aarhus extends Settlement("Aarhus", UUID.randomUUID(),
      (South(58, 0, 0), West(183, 0, 0)), Moons.Titan, 4.km,
      Titanian, Seq(Skandinaviska, English),
      Seq(Art, Education, Politics, Research)) {
      override def extraInfo = Seq(("Population" -> "5million+"), ("Notes" -> "Location of TAU and TTI campi"));
    }
    object NewQuebec extends Settlement("New Quebec", UUID.randomUUID(),
      (South(4, 0, 0), East(20, 0, 0)), Moons.Titan, 3.km,
      Titanian, Seq(English, French, Skandinaviska),
      Seq(Crime, MorphProduction, Sports)) {
      override def extraInfo = Seq(("Population" -> "1.5million+"));
    }
    object Huvudskar extends Settlement("Huvudskär", UUID.randomUUID(),
      (North(78, 0, 0), East(89, 0, 0)), Moons.Titan, 0.5.km,
      Titanian, Seq(Skandinaviska),
      Seq.empty) {
    }
    object Longueil extends Settlement("Longueil", UUID.randomUUID(),
      (North(16, 0, 0), East(101, 0, 0)), Moons.Titan, 1.km,
      Titanian, Seq(French),
      Seq.empty) {
    }
    object Stykkisholmur extends Settlement("Stykkishólmur", UUID.randomUUID(),
      (South(22, 0, 0), East(99, 0, 0)), Moons.Titan, 1.km,
      Titanian, Seq(Skandinaviska),
      Seq(Mining, Tourism)) {
    }
    object CommonwealthHub {

      val name = "Commonwealth Hub";
      val moon = Moons.Titan;
      val allegiance: Allegiance = Titanian;
      val langs: Seq[Language] = Seq(Skandinaviska, English, French);
      val industries: Seq[Industry] = Seq(Trade, Transport);
      val geoSync: Length = 75573.4691.km;
      val info = Seq(("Population" -> "200 000"));

      object Atmos extends SyncOrbitStation(name, UUID.randomUUID(),
        (South(0, 0, 0), West(122, 0, 0)), (geoSync - moon.radius), moon, // no idea where exactly it is
        allegiance, langs, industries) {
        override def extraInfo = info;
      }
      object Hab extends Habitat(name, UUID.randomUUID(), squants.Kilograms(2.4e10),
        Cluster, moon,
        allegiance, langs, industries) with Orbiting {
        val orbit = new ConstantOrbit(0.0, geoSync, 299.56024.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less this^^
        override def extraInfo = info;
      }
    }
  }
  import Titan._;

  val forPlanet: Map[UUID, Seq[AtmosphericObject]] = Map(
    Planets.Earth.id -> Seq(Greenwich, KilimanjaroSE, KilimanjaroSEOrbital, NewYorkCity, Bejing, PanamaCity, Tashkent, Moscow, Hawaii, RioDeJaneiro, CapeTown),
    Planets.Mars.id -> Seq(OlympusCity, Tether, VallesNewShanghai, NoctisQianjiao, Elysium, Ashoka, NewDazhai, PilsenerCity, PathfinderGate, PathfinderCity, Anyang, Qurain, Kartika),
    Planets.Venus.id -> Seq(Octavia, AphroditePrime, Lucifer, TheShack, Parvarti, Shukra, Etemenanki, DeepReach),
    Planets.Mercury.id -> Seq(AlHamadhanj, Caloris18, Cannon, DelacroixShelley, Lumina),
    Planets.Ceres.id -> Seq(Aventine, Wujec, Piazzi.atmos, Proserpina));

  val forMoon: Map[UUID, Seq[AtmosphericObject]] = Map(
    Moons.Luna.id -> Seq(Erato, Nectar, Shackle, CleverHands, TheColony, Feynman, Muir, NewMumbai),
    Moons.Ganymede.id -> Seq(LibertyCity, LibertyStation.atmos),
    Moons.Io.id -> Seq(MauiPatera),
    Moons.Callisto.id -> Seq(Hyoden, Gerdr),
    Moons.Europa.id -> Seq(ConamaraChaos, Conamara, Pwyll, TheNorns),
    Moons.Dione.id -> Seq(Thoroughgood),
    Moons.Enceladus.id -> Seq(Profunda),
    Moons.Mimas.id -> Seq(HarmoniusAnarchy),
    Moons.Rhea.id -> Seq(Habitats.KronosCluster.Atmos),
    Moons.Tethys.id -> Seq(Godwinhead),
    Moons.Titan.id -> Seq(Nyhavn, Aarhus, NewQuebec, Huvudskar, Longueil, Stykkisholmur, CommonwealthHub.Atmos));
}
