package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{ AstronomicalUnits => AU }
import squants.time._
import squants.motion._

import Languages._
import Polities.{PlanetaryConsortium => _, _}
import Corps._
import Industries._

object Habitats {

    object Remembrance extends Habitat("Remembrance", UUID.randomUUID(), Kilograms(4.8e10),
        ONeillCyliner(Kilometers(70), Kilometers(8)), Planets.Earth,
        LunarLagrangeAlliance, Seq(English, Hindi, Wu),
        Seq(Shipping, Trade, Information, Politics)) with Orbiting {
        val orbit = new LissajousOrbit(EarthLunaL4, Kilometers(35000.0), Kilometers(25000.0), DegreesPerSecond(0.02015), DegreesPerSecond(0.02015), 3.229, Degrees(0.0), Degrees(0.0));
        override def extraInfo = Seq(("Population" -> "2 million+"));
        //        override def description = Some("""
        //The center of the Lunar-Lagrange Alliance and the largest station near Earth, Remembrance dominates the Earth-Luna L4 point and houses over two million people. Though it is the largest O’Neill cylinder in existence, it is still overpopulated, so most of the station is cramped, dirty, smelly, and dangerous. The station itself actually consists of two counter-rotating cylinders laid end-to-end, each 35 kilometers in length and 8 kilometers in diameter, and providing approximate Earth gravity. The most crowded cylinder, home to the more impoverished residents and indentures, is continuously pushing its environmental systems to their maximum, and suffers regular breakdowns – as evidenced by the septic growths and smells. The other half of the habitat fares slightly better, thanks to better air scrubbers, and it is here that the towers of the upper-class elites, including the LLA officials, rise above all, clean and airy and shining. There have been several proposals to expand Remembrance itself, adding another cylinder in order to accommodate its increased population, but doing so would require a substantial amount of cred. Most of the elite refuse to be bothered; after all, they’re not the ones being crowded. Still, the effects do spill over into the business section and even around the towers, and Remembrance suffers an unfortunate amount of vice and crime.
        //""");
    }

    object Elegua extends Habitat("Elegua", UUID.randomUUID(), Kilograms(4.8e10),
        // assume sized based on population comparison to Island Two (https://en.wikipedia.org/wiki/Bernal_sphere)
        ModifiedBernalSphere(Kilometers(1.8)), Planets.Earth,
        TerraGenesis, Seq(Dutch, English, Vietnamese),
        Seq(Terraforming, EcosystemManagement, EnvironmentalData)) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(112000), Degrees(12.5), Degrees(0.0), Degrees(0.0), Degrees(0.0), this.mass, this.centre);
        override def extraInfo = Seq(("Population" -> "120 000"));
    }
    
    object FreshKills extends Habitat("Fresh Kills", UUID.randomUUID(), Kilograms(4.8e10),
        Cluster, Planets.Earth,
        Independent(Scum), Seq(English, French, Tamil),
        Seq(Salvage)) with Orbiting {
        val orbit = new LissajousOrbit(EarthLunaL5, Kilometers(19000.0), Kilometers(12000.0), DegreesPerSecond(0.0310), DegreesPerSecond(0.0310), 2.10780125, Degrees(90.0), Degrees(45.0));
        //override def extraInfo = Seq(("Population" -> "120 000"));
    }
    
    object Hexagon extends Habitat("Hexagon", UUID.randomUUID(), Kilograms(4.8e10),
        ModifiedTorus(Kilometers(1.79), Kilometers(0.13)), Planets.Earth,
        PlanetaryConsortium(DirectAction), Seq(English, German, Mandarin),
        Seq(MilitaryTech, Research, Training)) with Orbiting {
        val orbit = new LissajousOrbit(EarthLunaL5, Kilometers(19000.0), Kilometers(12000.0), DegreesPerSecond(0.0310), DegreesPerSecond(0.0310), 2.10780125, Degrees(270.0), Degrees(315.0));
        //override def extraInfo = Seq(("Population" -> "120 000"));
    }
    
    object HotelCalifornia extends Habitat("Hotel California", UUID.randomUUID(), Kilograms(4.8e10),
        Asteroid("Beehive/Dome", Kilometers(3.0), Kilometers(1.2), Kilometers(1.5)), Planets.Earth,
        Independent(Brinker), Seq(Bengali, Hindi),
        Seq()) with Orbiting {
        val orbit = new LissajousOrbit(EarthLunaL4, Kilometers(70000.0), Kilometers(42000.0), DegreesPerSecond(0.017), DegreesPerSecond(0.034), 3.82387286764706, Degrees(35.0), Degrees(125.0));
        //override def extraInfo = Seq(("Population" -> "120 000"));
    }
    
    object Paradise extends Habitat("Paradise", UUID.randomUUID(), Kilograms(2.4e10),
        Torus(Kilometers(0.845), Kilometers(0.65)), Planets.Earth,
        Polities.PlanetaryConsortium, Seq(English, Italian),
        Seq(ResortServices, Tourism)) with Orbiting {
        val orbit = new LissajousOrbit(SunEarthL1, Kilometers(4300.0), Kilometers(1200.0), DegreesPerSecond(0.017), DegreesPerSecond(0.017), 3.82387286764706, Degrees(0.0), Degrees(0.0));
        //override def extraInfo = Seq(("Population" -> "120 000"));
    }
    
    object VoNguyen extends Habitat("Vo Nguyen", UUID.randomUUID(), Kilograms(2.4e10),
        ONeillCyliner(Kilometers(64), Kilometers(4)), Planets.Earth,
        LunarLagrangeAlliance, Seq(French, Vietnamese),
        Seq(Research, Salvage)) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(42164), Degrees(0.5), Degrees(0.0), Degrees(0.0), Degrees(0.0), this.mass, this.centre);
    }
    
    object SeleneStation extends Habitat("Selene Station", UUID.randomUUID(), Kilograms(2.4e10), // I tried to calculate that from the speed and orbit given in the book, but I always come up with negative mass solutions^^
        Torus(Kilometers(6.0), Kilometers(0.7)), Moons.Luna,
        Polities.PlanetaryConsortium, Seq(English),
        Seq(Transport, ZeroGManufacturing)) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(11737.1), Degrees(0.5), Degrees(0.0), Degrees(0.0), Degrees(0.0), this.mass, this.centre);
        override def extraInfo = Seq(("Population" -> "3million+"));
    }
    
    object KorolevShipyards extends Habitat("Korolev Shipyards", UUID.randomUUID(), Kilograms(2.4e10),
        Cluster, Moons.Luna,
        LunarLagrangeAlliance, Seq(Russian),
        Seq(Transport, ZeroGManufacturing)) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(16737.1), Degrees(5.4), Degrees(0.0), Degrees(0.0), Degrees(180.0), this.mass, this.centre);
        override def extraInfo = Seq(("Population" -> "25 000"), ("Owner" -> "Starware"));
    }
    
    object MVCPR extends Habitat("Mare Vaporium Circumlunar People's Republic", UUID.randomUUID(), Kilograms(2.4e10),
        ONeillCyliner(Kilometers(64), Kilometers(4)), Moons.Luna,
        LunarLagrangeAlliance, Seq(Cantonese, Mandarin),
        Seq(Counterfeiting, Salvage, Scavenging)) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(27737.1), Degrees(23.5), Degrees(0.0), Degrees(0.0), Degrees(90.0), this.mass, this.centre);
        //override def extraInfo = Seq(("Population" -> "25 000"), ("Owner" -> "Starware"));
    }
    
    object Mitre extends Habitat("Mitre", UUID.randomUUID(), Kilograms(1.1e9),
        Cluster, Moons.Luna,
        Independent(Argonauts), Seq(English, Mandarin),
        Seq(Research, Information)) with Orbiting {
        val orbit = new ConstantOrbit(0.005, Kilometers(75737.1), Degrees(87.8), Degrees(30.0), Degrees(80.0), Degrees(210.0), this.mass, this.centre);
        //override def extraInfo = Seq(("Population" -> "25 000"), ("Owner" -> "Starware"));
    }
    
    object Tsukomo extends Habitat("Tsukomo", UUID.randomUUID(), Kilograms(1.0e10),
        Torus(Kilometers(0.9), Kilometers(0.065)), Moons.Luna,
        LunarLagrangeAlliance, Seq(Japanese),
        Seq()) with Orbiting {
        val orbit = new ConstantOrbit(0.0, Kilometers(43737.1), Degrees(156.8), Degrees(0.0), Degrees(0.0), Degrees(175.0), this.mass, this.centre);
        override def extraInfo = Seq(("Population" -> "10 000"), ("Owner" -> "Go-nin Group"));
    }

    val list = Seq(Remembrance, Elegua, FreshKills, Hexagon, HotelCalifornia, Paradise, VoNguyen,
            SeleneStation, KorolevShipyards, MVCPR, Mitre, Tsukomo);
}