package com.larskroll.ep.mapviewer.data

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
        object Qurain extends Settlement("Qurain", UUID.randomUUID(),
            (South(15, 3, 0), West(179, 45, 0)), Planets.Mars, Kilometers(5),
            AbandonedToTITANs, Seq(Arabic),
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
    import Mercury._

    val forPlanet = Map(
        Planets.Earth.id -> Seq(Greenwich, KilimanjaroSE, KilimanjaroSEOrbital, NewYorkCity, Bejing, PanamaCity, Tashkent, Moscow, Hawaii, RioDeJaneiro, CapeTown),
        Planets.Mars.id -> Seq(OlympusCity, Tether, VallesNewShanghai, NoctisQianjiao, Elysium, Ashoka, NewDazhai, PilsenerCity, PathfinderGate, PathfinderCity, Qurain),
        Planets.Venus.id -> Seq(Octavia, AphroditePrime, Lucifer, TheShack, Parvarti, Shukra, Etemenanki, DeepReach),
        Planets.Mercury.id -> Seq(AlHamadhanj, Caloris18, Cannon, DelacroixShelley, Lumina));

    val forMoon = Map(
        Moons.Luna.id -> Seq(Erato, Nectar, Shackle, CleverHands, TheColony, Feynman, Muir, NewMumbai));
}