package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{ AstronomicalUnits => AU }
import squants.time._
import squants.motion._

import Languages._
import Polities.{ PlanetaryConsortium => _, _ }
import Corps._
import Industries._

object HabitatsRimward {

  import ExtraUnits._;

  // Main Belt
  object Aspis extends Habitat("Aspis", UUID.randomUUID(), Kilograms(3.6e11),
    ONeillCylinder(Kilometers(192), Kilometers(8)), Stars.Sol,
    Ultimates, Seq(Burmese, English, Russian),
    Seq()) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.15, 2.3.AU, 3.5.º, 283.0.º, 289.0.º, 90.0.º, this.mass, Stars.Sol.mass);
    override def extraInfo = Seq(("Population" -> "20 000"));
  }

  object Extropia extends Habitat("Extropia (44 Nysa)", UUID.randomUUID(), Kilograms(3.6e11),
    Asteroid("Beehive", 113.0.km, 67.0.km, 65.0.km), Stars.Sol,
    Extropian, Seq(Cantonese, English, Russian, Spanish),
    Seq(Trade, Shipping, Politics)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.148158617, 2.42380478.AU, 3.7028885.º, 131.59519.º, 342.52066.º, 118.743236.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    override def extraInfo = Seq(("Population" -> "10 million+"));
  }

  object Legba extends Habitat("Legba", UUID.randomUUID(), Kilograms(4.8e10),
    Cluster, Stars.Sol,
    NineLives, Seq(French, Spanish),
    Seq(Crime)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.253, 2.44.AU, 3.68.º, 31.0.º, 244.0.º, 0.0.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    //override def extraInfo = Seq(("Population" -> "10 million+"));
  }

  object NovaYork extends Habitat("Nova York (9 Metis)", UUID.randomUUID(), Kilograms(1.47e19),
    Asteroid("Beehive", 222.0.km, 182.0.km, 130.0.km), Stars.Sol,
    Independent(Autonomist), Seq(English, French, Wu),
    Seq(Finance, Art, Tourism)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.122, 2.387.AU, 5.576.º, 68.982.º, 5.489.º, 274.183.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    override def extraInfo = Seq(("Population" -> "500 000"));
  }

  object Pallas extends Habitat("Pallas (2 Pallas)", UUID.randomUUID(), Kilograms(2.11e20),
    Asteroid("Beehives/TinCan/Domes", 550.0.km, 516.0.km, 476.0.km), Stars.Sol,
    Independent(NightCartel), Seq(English, Russian, Spanish),
    Seq(Crime, Mining, Research, SoftwareDesign)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.23127363, 2.77160611.AU, 34.840998.º, 173.096248.º, 309.930328.º, 78.228704.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    override def extraInfo = Seq(
      ("Population" -> "75 000"),
      ("Note" -> "Pallas is orbited by a Cole Bubble called Palladion, and a Torus called Wits (Wheel in the sky). Most of its population resides on these two habitats."));
  }

  object Starwell extends Habitat("Starwell", UUID.randomUUID(), Kilograms(2.4e10),
    Torus(Kilometers(0.845), Kilometers(0.065)), Stars.Sol,
    ExoTech, Seq(English, Mandarin, Serbian),
    Seq(AI, ComSystems, SoftwareDesign, ImplantDesign, Research)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.06, 2.71.AU, 0.2.º, 12.0.º, 47.0.º, 0.0.º, this.mass, Stars.Sol.mass);
    override def extraInfo = Seq(
      ("Population" -> "50 000"),
      ("Note" -> "There is also a secondary Cluster, that is isolated for research."));
  }

  object Vesta extends Habitat("Vesta (4 Vesta)", UUID.randomUUID(), Kilograms(2.59076e20),
    Asteroid("Beehives/TinCan/Domes", 572.0.km, 557.0.km, 446.0.km), Stars.Sol,
    Independent(), Seq(Arabic, Hindi, Russian),
    Seq(ZeroGManufacturing, AerospaceEngineering, Mining, RocketDesign)) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.08874, 2.36179.AU, 7.14043.º, 103.85136.º, 151.19853.º, 20.86384.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    override def extraInfo = Seq(
      ("Population" -> "250 000"),
      ("Note" -> "Vesta is orbited by a Cole Bubble called Vesta Prime, and the Starware shipyards."));
  }

  object Zombieland extends Habitat("Zombieland (349 Dembrowska)", UUID.randomUUID(), Kilograms(3.58e18),
    Asteroid("Beehive and Cluster", 145.0.km, 140.0.km, 69.0.km), Stars.Sol,
    Independent(CorpAsPolity(Zrbny)), Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOriginOrbit(0.091473, 2.92379.AU, 8.2461.º, 32.351.º, 346.225.º, 306.898.º, this.mass, Stars.Sol.mass); // wrong epoch for mean anomaly
    //override def extraInfo = Seq(("Population" -> "250 000"));
  }

  // Jupiter
  object Amalthea extends Habitat("Amalthea (Solano)", UUID.randomUUID(), Kilograms(2.08e18),
    Asteroid("Reagan Cylinder", 250.0.km, 146.0.km, 128.0.km), Planets.Jupiter,
    Jovian, Seq(English, Spanish), Seq(Security, Research)) with Orbiting {
    val orbit = new ConstantOrbit(0.00319, 181365.84.km, 4.807.º, 140.7.º, 107.6.º, 241.521.º, this.mass, this.centre); // at 2451890.0 JED
  }
  object TheCastle extends Habitat("The Castle", UUID.randomUUID(), Kilograms(2.4e10),
    Asteroid("Cluster/Beehive", 5.0.km, 2.2.km, 2.8.km), Moons.Ganymede,
    Jovian, Seq(English, Spanish), Seq(Security, SignalAnalysis)) with Orbiting {
    val orbit = new ConstantOrbit(0.0, 64000.0.km, 312.57.º, 232.20.º, 0.0.º, 0.0.º, this.mass, this.centre);
  }
  object TheHolySee extends Habitat("The Holy See", UUID.randomUUID(), Kilograms(2.4e12),
    Asteroid("Reagan Cylinder", 20.0.km, 12.0.km, 9.0.km), Moons.Ganymede,
    Protectorate(Jovian, CatholicChurch), Seq(Latin, English, Spanish), Seq(Religion)) with Orbiting {
    val orbit = new ConstantOrbit(0.0, 79000.0.km, 342.57.º, 32.20.º, 89.0.º, 0.0.º, this.mass, this.centre);
  }

  object JSFAntimatterFactory extends Habitat("JSF Antimatter Factory", UUID.randomUUID(), Kilograms(2.4e10),
    Asteroid("Cluster/Beehive", 5.0.km, 2.2.km, 2.8.km), Moons.Io,
    Jovian, Seq(English, Spanish), Seq(Antimatter, Military)) with Orbiting {
    val orbit = new ConstantOrbit(0.0, 47000.0.km, 322.0.º, 89.0.º, 55.0.º, 0.0.º, this.mass, this.centre);
  }

  object Aoede extends Habitat("Aoede", UUID.randomUUID(), Kilograms(1.4e10), // infos are shaky
    Asteroid("Unknown", 4.0.km, 4.0.km, 4.0.km), Planets.Jupiter,
    Protectorate(Jovian, NoPolity), Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.432, 23.980e6.km, 158.3.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
  }

  object Aitne extends Habitat("Aitne", UUID.randomUUID(), Kilograms(1.4e8), // infos are shaky
    Asteroid("Beehive", 1.5.km, 1.5.km, 1.5.km), Planets.Jupiter,
    CarmeCompact, Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.264, 23.230e6.km, 165.1.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Population" -> "8 000 (Compact)"));
  }

  object Kale extends Habitat("Kale", UUID.randomUUID(), Kilograms(0.4e8), // infos are shaky
    Asteroid("Beehive", 1.0.km, 1.0.km, 1.0.km), Planets.Jupiter,
    CarmeCompact, Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.260, 23.220e6.km, 165.0.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Population" -> "8 000 (Compact)"));
  }

  object Taygete extends Habitat("Taygete", UUID.randomUUID(), Kilograms(2.4e9), // infos are shaky
    Asteroid("Beehive", 2.5.km, 2.5.km, 2.5.km), Planets.Jupiter,
    CarmeCompact, Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.251, 23.360e6.km, 165.2.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Population" -> "8 000 (Compact)"));
  }

  object Callirrhoe extends Habitat("Callirrhoe", UUID.randomUUID(), Kilograms(2.4e10), // infos are shaky
    Asteroid("Dome", 8.6.km, 8.6.km, 8.6.km), Planets.Jupiter,
    Protectorate(Jovian, IndependentPolity), Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.2796, 24.099e6.km, 147.080.º, 283.104.º, 23.909.º, 107.962.º, this.mass, this.centre, true); // no idea which Epoch
  }

  object Carpo extends Habitat("Carpo", UUID.randomUUID(), Kilograms(2.4e9), // infos are shaky
    Asteroid("Beehive/Simulspace", 3.0.km, 3.0.km, 3.0.km), Planets.Jupiter,
    IndependentPolity, Seq(English), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.430, 16.990e6.km, 51.4.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Population" -> "17 000 (98% infomorphs)"));
  }

  object Euanthe extends Habitat("Euanthe", UUID.randomUUID(), Kilograms(2.4e10), // infos are shaky
    Asteroid("Dome/Beehive", 3.0.km, 3.0.km, 3.0.km), Planets.Jupiter,
    Protectorate(Jovian, IndependentPolity), Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.232, 20.800e6.km, 148.9.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Population" -> "2 000"));
  }

  object Helike extends Habitat("Helike", UUID.randomUUID(), Kilograms(2.4e10), // infos are shaky
    Asteroid("Reagan Cylinder", 4.0.km, 4.0.km, 4.0.km), Planets.Jupiter,
    NoPolity, Seq(), Seq()) with Orbiting {
    val orbit = new ConstantOrbit(0.156, 21.260e6.km, 154.8.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, true); // best info I could find (http://nssdc.gsfc.nasa.gov/planetary/factsheet/joviansatfact.html)
    override def extraInfo = Seq(("Notes" -> "Abandonded due to decompression."));
  }

  // Jovian Trojans/Greeks
  object Locus extends Habitat("Locus", UUID.randomUUID(), Kilograms(2.4e8),
    NuestroShell(11.0.km), Stars.Sol,
    Autonomist, Seq(AnyLang), Seq(Art, Research, Military)) with Orbiting {
    val orbit = new LissajousOrbit(JovianTrojans, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(35.0), Degrees(125.0));
    override def extraInfo = Seq(("Population" -> "1 million"));
  }

  object CSquat extends Habitat("C Squat", UUID.randomUUID(), Kilograms(2.4e8),
    Asteroid("Beehive", 3.0.km, 3.0.km, 3.0.km), Stars.Sol,
    Autonomist, Seq(English, Hindi, Spanish), Seq(Art)) with Orbiting {
    val orbit = new LissajousOrbit(JovianGreeks, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(35.0), Degrees(125.0));
    override def extraInfo = Seq(("Population" -> "500"));
  }

  object CasaArturo extends Habitat("Casa Arturo", UUID.randomUUID(), Kilograms(2.4e8),
    NuestroShell(1.0.km), Stars.Sol,
    Autonomist, Seq(English, Mandarin, Spanish), Seq(RocketDesign, Mining, ZeroGManufacturing)) with Orbiting {
    val orbit = new LissajousOrbit(JovianGreeks, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(98.0), Degrees(12.0));
    override def extraInfo = Seq(("Population" -> "1 200"));
  }

  object CatalHayuk extends Habitat("Catal Hayuk", UUID.randomUUID(), Kilograms(2.4e8),
    ONeillCylinder(Kilometers(8), Kilometers(4)), Stars.Sol,
    Independent(Brinker), Seq(Kurdish, Turkish), Seq(Research)) with Orbiting {
    val orbit = new LissajousOrbit(JovianTrojans, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(98.0), Degrees(12.0));
    override def extraInfo = Seq(("Population" -> "3 500"));
  }

  object Exarchia extends Habitat("Exarchia", UUID.randomUUID(), Kilograms(2.4e8),
    Cluster, Stars.Sol,
    Autonomist, Seq(English, Greek, Mandarin), Seq(Hacktivism)) with Orbiting {
    val orbit = new LissajousOrbit(JovianGreeks, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(270.0), Degrees(157.0));
    override def extraInfo = Seq(("Population" -> "4 500"));
  }

  object Lot49 extends Habitat("Lot49", UUID.randomUUID(), Kilograms(2.4e8),
    NuestroShell(1.0.km), Stars.Sol,
    Autonomist, Seq(English, Portuguese, Thai), Seq(Transport, Shipping)) with Orbiting {
    val orbit = new LissajousOrbit(JovianGreeks, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(170.0), Degrees(187.0));
    override def extraInfo = Seq(("Population" -> "400"));
  }

  object Respect extends Habitat("Respect", UUID.randomUUID(), Kilograms(2.4e8),
    BernalSphere(8.0.km), Stars.Sol,
    IndependentNamed("Sapient Uplift"), Seq(Javanese, Tamil, Spanish), Seq()) with Orbiting {
    val orbit = new LissajousOrbit(JovianTrojans, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(132.0), Degrees(43.0));
    override def extraInfo = Seq(("Population" -> "3 000"));
  }

  object Turing extends Habitat("Turing", UUID.randomUUID(), Kilograms(2.4e8),
    Asteroid("Beehive", 1.0.km, 0.5.km, 0.8.km), Stars.Sol,
    NoPolity, Seq(), Seq()) with Orbiting {
    val orbit = new LissajousOrbit(JovianTrojans, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(32.0), Degrees(110.0));
    override def extraInfo = Seq(("Notes" -> "Nuked and Abandoned"));
  }

  object Winter extends Habitat("Winter", UUID.randomUUID(), Kilograms(2.4e8),
    Asteroid("Cole bubble", 8.0.km, 5.2.km, 4.0.km), Stars.Sol,
    Independent(Brinker), Seq(Arabic, Italian), Seq()) with Orbiting {
    val orbit = new LissajousOrbit(JovianTrojans, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(213.0), Degrees(67.0));
    override def extraInfo = Seq(("Population" -> "10 000"));
  }

  // Saturn
  object Volkograd extends Habitat("Volkograd (Atlas)", UUID.randomUUID(), Kilograms(6.6e15),
    Asteroid("Beehive", 40.8.km, 35.4.km, 18.8.km), Planets.Saturn,
    Hypercorp(Volkov), Seq(Czech, Slovak),
    Seq(Mining, Shipping)) with Orbiting {
    val orbit = new ConstantOrbit(0.0012, 0.137670e6.km, 29.523.º, 0.500.º, 331.521.º, 129.760.º, this.mass, this.centre); // wrong Epoch (2453005.5 JED)
    override def extraInfo = Seq(("Population" -> "50 000"));
  }

  object Bright extends Habitat("Bright (Telesto)", UUID.randomUUID(), Kilograms(4.8e10),
    HamiltonCylinder(8.0.km, 1.0.km), Planets.Saturn,
    Hypercorp(Acumenic), Seq(English, German, Mandarin),
    Seq(Research)) with Orbiting {
    val orbit = new LissajousOrbit(new L4(Planets.Saturn, Moons.Tethys), 1205.0.km, 798.0.km, RotationPeriod(Days(0.3)), RotationPeriod(Days(0.14)), 2.934, 213.0.º, 67.0.º);
    override def extraInfo = Seq(("Population" -> "30 000")); // conflicting data on the same page (7000)
  }

  object Kiviuq extends Habitat("Kiviuq Monastery", UUID.randomUUID(), Kilograms(6.6e12),
    Asteroid("Beehive", 16.0.km, 16.0.km, 16.0.km), Planets.Saturn, // shaky data
    IndependentPolity, Seq(Greek, Russian),
    Seq(Religion)) with Orbiting {
    val orbit = new ConstantOrbit(0.3288, 11.111e6.km, 45.71.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // no idea
    override def extraInfo = Seq(("Population" -> "50"));
  }

  // Note that the books give no indication at all where it is...I made all of this up^^
  object MeatHab extends Habitat("MeatHab", UUID.randomUUID(), Kilograms(4.8e10),
    UniqueStation("MeatHab"), Planets.Saturn,
    IndependentPolity, Seq(Mandarin, Polish, Turkish),
    Seq(Art)) with Orbiting {
    val orbit = new ConstantOrbit(0.000144, 335847.0.km, 29.3.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre, retrograde = true); // no idea
    override def extraInfo = Seq(("Population" -> "500"));
  }

  object IZulu extends Habitat("iZulu (Pan)", UUID.randomUUID(), Kilograms(4.95e15),
    Asteroid("Beehive", 34.4.km, 31.4.km, 20.8.km), Planets.Saturn,
    Autonomist, Seq(Afrikaans, English, Xhosa, Zulu),
    Seq(SoftwareDesign)) with Orbiting {
    val orbit = new ConstantOrbit(0.0000144, 133584.0.km, 29.219.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // no idea
    override def extraInfo = Seq(("Population" -> "1.1 million"));
  }

  object PhelansRecourse extends Habitat("Phelan's Recourse", UUID.randomUUID(), Kilograms(3.2e7),
    Cluster, Planets.Saturn,
    AA(Scum), Seq(English, Mandarin, Skandinaviska),
    Seq(Entertainment)) with Orbiting {
    val orbit = VariableOrbit(0.792137, 681795.km, 87.0.º,
      ConstantAngle(0.0.º),
      ConstantAngle(0.0.º),
      0.0.º, this.mass, this.centre); // no idea
    override def extraInfo = Seq(("Population" -> "250 000"));
  }

  object MarseillesPrometheus extends Habitat("Marseilles (Prometheus)", UUID.randomUUID(), Kilograms(1.595e17),
    Asteroid("Beehive", 135.6.km, 79.4.km, 59.4.km), Planets.Saturn,
    AA(Titanian), Seq(French, Skandinaviska, Vietnamese),
    Seq(Antimatter, Chemistry, Mining, Transport)) with Orbiting {
    val orbit = new ConstantOrbit(0.0022, 139380.0.km, 29.223.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // no idea
    override def extraInfo = Seq(("Population" -> "800 000"));
  }

  object KronosCluster {
    import Geography._

    val name = "Kronos Cluster";
    val moon = Moons.Rhea;
    val allegiance: Allegiance = Independent(Anarchists, Criminal, Ultimates);
    val langs: Seq[Language] = Seq(Cantonese, Dutch, Turkish);
    val industries: Seq[Industry] = Seq(Art, Counterfeiting, Crime, Entertainment, Smuggling, Tourism, Trade, WaterExtraction);
    val geoSync: Length = 8407.2522.km;
    val info = Seq(("Population" -> "950 000"));

    object Atmos extends SyncOrbitStation(name, UUID.randomUUID(),
      (North(0, 0, 0), West(0, 0, 0)), (geoSync - moon.radius), moon, // no idea where exactly it is
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

  object Salah extends Habitat("Salah (Calypso)", UUID.randomUUID(), Kilograms(4.8e10),
    HamiltonCylinder(8.0.km, 1.0.km), Planets.Saturn,
    Contested, Seq(Arabic, Punjabi, Turkish),
    Seq(Finance, Religion)) with Orbiting {
    val orbit = new LissajousOrbit(new L5(Planets.Saturn, Moons.Tethys), 1215.0.km, 788.0.km, RotationPeriod(Days(0.365)), RotationPeriod(Days(0.12)), 2.534, 0.0.º, 0.0.º);
    override def extraInfo = Seq(("Population" -> "215 000"));
  }

  object Epimetheus extends Habitat("Twelve Commons (Epimetheus)", UUID.randomUUID(), Kilograms(5.266e17),
    Asteroid("Beehive", 129.8.km, 114.km, 106.2.km), Planets.Saturn,
    AA(Anarchists), Seq.empty,
    Seq.empty) with Orbiting {
    val orbit = new ConstantOrbit(0.0098, 151410.km, 28.986.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
  }

  object Janus extends Habitat("Twelve Commons (Janus Commons)", UUID.randomUUID(), Kilograms(1.8975e18),
    Asteroid("Beehive", 203.km, 185.km, 152.6.km), Planets.Saturn,
    AA(Anarchists), Seq(English, French, Spanish),
    Seq(Art, Chemistry)) with Orbiting {
    val orbit = new ConstantOrbit(0.0068, 151460.km, 29.123.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "900 000"));
  }

  object DIYShipyards extends Habitat("Twelve Commons (DIY Shipyards)", UUID.randomUUID(), Kilograms(2.4e10),
    Cluster, Planets.Saturn,
    AA(Anarchists), Seq(Arabic, Malay),
    Seq(AerospaceEngineering, ZeroGManufacturing)) with Orbiting {
    val orbit = new ConstantOrbit(0.0076, 151450.km, 29.203.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "10 000"));
  }

  object LongHaul extends Habitat("Twelve Commons (Long Haul)", UUID.randomUUID(), Kilograms(2.4e10),
    Torus(Kilometers(0.845), Kilometers(0.065)), Planets.Saturn,
    AA(Anarchists), Seq(English, Hindi, Mandarin),
    Seq.empty) with Orbiting {
    val orbit = new ConstantOrbit(0.0072, 151440.km, 29.263.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "20 000"));
  }

  object NguyensCompact extends Habitat("Twelve Commons (Nguyen's Compact)", UUID.randomUUID(), Kilograms(2.4e8),
    Asteroid("Cole bubble", 8.0.km, 5.2.km, 4.0.km), Planets.Saturn,
    AA(Anarchists), Seq(French, Khmer, Vietnamese),
    Seq.empty) with Orbiting {
    val orbit = new ConstantOrbit(0.0082, 151430.km, 29.262.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "80 000"));
  }

  object RedEmmasDance extends Habitat("Twelve Commons (Red Emma's Dance)", UUID.randomUUID(), Kilograms(2.4e10),
    Asteroid("Track", 15.0.km, 12.2.km, 6.0.km), Planets.Saturn,
    AA(Anarchists), Seq(Greek, Italian),
    Seq(Art, MorphDesign)) with Orbiting {
    val orbit = new ConstantOrbit(0.0085, 151420.km, 29.260.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "2 000"));
  }

  object SmallMajesties extends Habitat("Twelve Commons (Small Majesties)", UUID.randomUUID(), Kilograms(1.4e10),
    Asteroid("Cluster", 13.0.km, 11.2.km, 4.0.km), Planets.Saturn,
    AA(Anarchists), Seq(Spanish, Tagalog),
    Seq(NanoTechnology)) with Orbiting {
    val orbit = new ConstantOrbit(0.0089, 151455.km, 29.258.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // more or less...
    override def extraInfo = Seq(("Population" -> "80"));
  }

  object Phoebe extends Habitat("Phoebe", UUID.randomUUID(), Kilograms(8.292e18),
    Asteroid("Cluster", 218.8.km, 217.0.km, 203.6.km), Moons.Titan,
    Titanian, Seq(Skandinaviska, English),
    Seq(Military)) with Orbiting {
    val orbit = new LissajousOrbit(new L1(Planets.Saturn, Moons.Titan), 1205.0.km, 780.0.km, RotationPeriod(Days(0.3)), RotationPeriod(Days(0.14)), 1.94, 0.0.º, 0.0.º);
    override def extraInfo = Seq(("Notes" -> "Home Port for Commonwealth Fleet"));
  }

  object Skathi extends Habitat("Skathi", UUID.randomUUID(), Kilograms(2.4e10),
    Asteroid("Cluster", 8.km, 6.km, 3.km), Moons.Titan,
    Titanian, Seq(Skandinaviska, English),
    Seq(Military)) with Orbiting {
    val orbit = new LissajousOrbit(new L4(Planets.Saturn, Moons.Titan), 32005.0.km, 17008.0.km, RotationPeriod(Days(1.96)), RotationPeriod(Days(1.49)), 1.84, 0.0.º, 0.0.º);
  }

  object Abramsen extends Habitat("Abramsen (S/2007 S 2)", UUID.randomUUID(), Kilograms(2.4e9),
    Asteroid("Cluster", 6.km, 5.km, 3.km), Moons.Titan,
    Titanian, Seq(Skandinaviska, English),
    Seq(Military)) with Orbiting {
    val orbit = new LissajousOrbit(new L5(Planets.Saturn, Moons.Titan), 32000.0.km, 17000.0.km, RotationPeriod(Days(1.91)), RotationPeriod(Days(1.41)), 3.84, 0.0.º, 0.0.º);
  }

  object Mankell extends Habitat("Mankell", UUID.randomUUID(), Kilograms(3.2e7),
    Cluster, Moons.Titan,
    Titanian, Seq(Skandinaviska, English),
    Seq(ComSystems, ZeroGManufacturing)) with Orbiting {
    val orbit = new ConstantOrbit(0.0034, 98000.0.km, 130.0.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // no idea
    override def extraInfo = Seq(("Population" -> "50 000"));
  }

  // Uranus System

  object Xiphos extends Habitat("Xiphos", UUID.randomUUID(), Kilograms(3.2e10),
    HamiltonCylinder(5.5.km, 1.0.km), Planets.Uranus,
    Ultimates, Seq(English, German, Hindi),
    Seq(Research, Security, HeavyIndustry, Sports)) with Orbiting {
    val orbit = new ConstantOrbit(0.00319, 36500.0.km, 0.0.º, 0.0.º, 0.0.º, 0.0.º, this.mass, this.centre); // not sure if the rings are inclined to match the equator
    override def extraInfo = Seq(("Population" -> "10 000"));
  }

  // Neptune System

  object Glitch extends Habitat("Glitch", UUID.randomUUID(), Kilograms(3.2e8),
    ProcessorLocus, Planets.Neptune,
    Independent(), Seq(English, Japanese, Russian),
    Seq(Research, AI, SoftwareDesign, Hacktivism)) with Orbiting {
    val orbit = new ConstantOrbit(0.00012, 167000.0.km, 43.0.º, 23.0.º, 112.0.º, 54.0.º, this.mass, this.centre);
    override def extraInfo = Seq(("Population" -> "20 000"));
  }

  object Mahogany extends Habitat("Mahogany", UUID.randomUUID(), Kilograms(1.4e10),
    Disc(500.0.m, 50.0.m), Planets.Neptune,
    AA(Mercurials), Seq(English, Indonesian, Spanish),
    Seq.empty) with Orbiting {
    val orbit = new ConstantOrbit(0.0021, 122000.0.km, 198.0.º, 98.0.º, 11.0.º, 20.0.º, this.mass, this.centre);
    override def extraInfo = Seq(("Population" -> "4 000"));
  }

  object Afrik extends Habitat("Afrik", UUID.randomUUID(), Kilograms(1.4e10),
    Torus(500.0.m, 50.0.m), Moons.Triton,
    Independent(Mercurials), Seq(Dutch, French),
    Seq(Bioengineering)) with Orbiting {
    val orbit = new ConstantOrbit(0.00011, 21000.0.km, 299.0.º, 41.0.º, 296.0.º, 20.0.º, this.mass, this.centre);
    override def extraInfo = Seq(("Population" -> "3 000"));
  }

  object Free extends Habitat("Free (Proteus)", UUID.randomUUID(), Kilograms(4.4e19),
    Asteroid("Beehive", 424.0.km, 390.0.km, 396.0.km), Planets.Neptune,
    Independent(), Seq(Arabic, English, Italian),
    Seq(Information, SignalAnalysis)) with Orbiting {
    val orbit = VariableOrbit(0.00053, Kilometers(117647), Degrees(30.607975),
      ConstantAngle(Degrees(0.0)),
      ConstantAngle(Degrees(0.0)),
      Degrees(0.0), mass, Planets.Neptune);
    //val rotation = ECR(320.7654228.ºd, 299.27.º, 42.91.º, 93.38.º);
    override def extraInfo = Seq(
      Designation(Planets.Neptune, 8),
      ("Population" -> "10 000"));
  }

  object Hawking extends Habitat("Hawking", UUID.randomUUID(), Kilograms(3.22e17),
    Asteroid("Beehive/Cluster", 112.0.km, 65.0.km, 66.0.km), Stars.Sol,
    IHypercorp, Seq(Arabic, English, German),
    Seq(RocketDesign)) with Orbiting {
    val orbit = new L4(Stars.Sol, Planets.Neptune);
    override def extraInfo = Seq(("Population" -> "3 000"));
  }

  object Ilmarinen extends Habitat("Ilmarinen", UUID.randomUUID(), Kilograms(3.23e17),
    Asteroid("Beehive/Cluster", 132.0.km, 76.0.km, 48.0.km), Stars.Sol,
    Independent(Anarchists, Argonauts), Seq(English, Farsi, Mandarin),
    Seq(Research, MorphDesign, Hacktivism, Art, Biodesign, ImplantDesign, Robotics, SoftwareDesign)) with Orbiting {
    val orbit = new L5(Stars.Sol, Planets.Neptune);
    override def extraInfo = Seq(("Population" -> "7 000"));
  }

  // Kuiper Belt

  object AlphaPlus extends Habitat("AlphaPlus", UUID.randomUUID(), Kilograms(2.4e10),
    BernalSphere(16.0.km), Stars.Sol,
    Independent(Brinker), Seq(English),
    Seq(Mining)) with Orbiting {
    val orbit = new LissajousOrbit(SolUranusL3, 20000000.0.km, 2500000.0.km, RotationPeriod(Days(50.0)), RotationPeriod(Days(10.0)), 3.229, Degrees(132.0), Degrees(43.0));
    override def extraInfo = Seq(("Population" -> "15 500"));
  }

  // Missing Conch Rimward p.137

  object NewSarpalius extends Habitat("New Sarpalius", UUID.randomUUID(), Kilograms(1.151e17), // based on https://en.wikipedia.org/wiki/7066_Nessus
    Asteroid("Beehive", 65.0.km, 61.0.km, 57.0.km), Stars.Sol,
    Polities.PlanetaryConsortium, Seq(Arabic, French),
    Seq(Research, Mining, Transport, Trade)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.51987, 24.59310.AU, 15.66868.º, 31.1557.º, 170.478.º, 77.00816.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "4000"));
  }

  object ThunderOnTheHorizon extends Habitat("Thunder on the Horizon (10370 Hylonome)", UUID.randomUUID(), Kilograms(1.85e17),
    Asteroid("Beehive and Track", 70.0.km, 67.0.km, 59.0.km), Stars.Sol,
    IndependentNamed("Bioconservative"), Seq(Burmese, English, Hindi),
    Seq.empty) with Orbiting {
    val orbit = ConstantOriginOrbit(0.2482, 25.152.AU, 4.1443.º, 178.08.º, 7.0279.º, 0.0.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "3000"));
  }

  object Langford extends Habitat("Langford", UUID.randomUUID(), Kilograms(3.2e7),
    Torus(120.0.m, 40.0.m), Stars.Sol,
    Argonauts, Seq(English, Skandinaviska, Mandarin, Russian),
    Seq(Research, Pharmaceuticals)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.158, 37.98.AU, 1.14.º, 308.3.º, 53.8.º, 41.3.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "300"));
  }

  object Haumea extends Habitat("Haumea", UUID.randomUUID(), Kilograms(4.006e21),
    Asteroid("Beehive and Tin Can", 2322.0.km, 1704.0.km, 1138.0.km), Stars.Sol,
    CriminalNamed("Măfēng gang"), Seq(Cantonese),
    Seq(Crime, Mining, Piracy)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.19126, 43.218.AU, 28.19.º, 121.79.º, 240.20.º, 209.07.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "Unknown"));
  }

  object Markov extends Habitat("Markov", UUID.randomUUID(), Kilograms(1.85e17),
    Asteroid("Beehive", 70.0.km, 67.0.km, 59.0.km), Stars.Sol,
    Argonauts, Seq(English, French, German),
    Seq(Research, SignalAnalysis)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.1, 40.0.AU, 2.0.º, 20.0.º, 200.0.º, 200.0.º, mass, this.centre.mass);
    override def extraInfo = Seq(
      ("Population" -> "3000"),
      ("Notes" -> "Position is a wild guess. The actual location is a secret."));
  }

  object HabitatOnTheRock extends Habitat("Habitat on the Rock", UUID.randomUUID(), Kilograms(3.2e7),
    Cluster, Stars.Sol,
    Independent(Brinker), Seq(English, Mandarin),
    Seq(Art)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.2, 42.5.AU, 3.0.º, 50.0.º, 90.0.º, 0.0.º, mass, this.centre.mass); // no idea
    override def extraInfo = Seq(("Population" -> "2500"));
  }

  object Whiskey extends Habitat("Whiskey", UUID.randomUUID(), Kilograms(1.0e10),
    Torus(1790.0.m, 130.0.m), Stars.Sol,
    Independent(), Seq(English, Hindi, Mandarin),
    Seq(Transport, Trade, Entertainment, Gambling, Information)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.12, 45.5.AU, 1.3.º, 37.0.º, 65.0.º, 0.0.º, mass, this.centre.mass); // no idea
    override def extraInfo = Seq(("Population" -> "12 500"));
  }

  // Missing Frozen Dreams p.141 Rimward

  object Tulihaend extends Habitat("Tulihänd", UUID.randomUUID(), Kilograms(-1.30117e22), // cheated mass to fix orbit
    Cluster, Planets.Pluto,
    Independent(Brinker), Seq(Estonian, Russian),
    Seq(Mining)) with Orbiting {
    // FIXME It's *supposed* to be at the barycentre between Pluto and Charon
    val orbit = VariableOrbit(0.0, 2110.0.km, 112.783.º,
      ConstantAngle(223.046.º),
      ConstantAngle(0.0.º),
      0.0.º, mass, Planets.Pluto);
    override def extraInfo = Seq(("Population" -> "9000"));
  }

  object Sedna extends Habitat("Sedna", UUID.randomUUID(), Kilograms(2.0e21),
    Asteroid("Beehive", 70.0.km, 67.0.km, 59.0.km), Stars.Sol,
    CriminalNamed("Dead Eye Society"), Seq(Arabic, Hindi),
    Seq(Mining, Piracy)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.854, 506.8.AU, 11.92872.º, 144.546.º, 311.29.º, 358.163.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "Unknown"));
  }

  object Haven extends Habitat("Haven", UUID.randomUUID(), Kilograms(3.2e7),
    Torus(120.0.m, 40.0.m), Stars.Sol,
    Independent(Brinker), Seq(English),
    Seq.empty) with Orbiting {
    val orbit = ConstantOriginOrbit(0.1, 140.0.AU, 4.0.º, 44.0.º, 12.0.º, 0.0.º, mass, this.centre.mass);
    override def extraInfo = Seq(("Population" -> "1000"));
  }

  object Thorne extends Habitat("Thorne", UUID.randomUUID(), Kilograms(3.2e7),
    Cluster, Stars.Sol,
    Argonauts, Seq(Mandarin),
    Seq(Research, ComSystems)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.01, 850.0.AU, 0.0.º, 0.0.º, 0.0.º, 0.0.º, mass, this.centre.mass); // far far out
    override def extraInfo = Seq(("Population" -> "570"));
  }

  // Oort Cloud

  object Wisdom extends Habitat("Wisdom", UUID.randomUUID(), Kilograms(1.3e12),
    Asteroid("Beehive", 40.0.km, 23.0.km, 20.0.km), Stars.Sol,
    Independent(Brinker), Seq(Wu),
    Seq(Art)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.01, 2500.0.AU, 0.0.º, 0.0.º, 0.0.º, 40.0.º, mass, this.centre.mass); // far far out
    override def extraInfo = Seq(("Population" -> "350"));
  }

  object TheNest extends Habitat("The Nest", UUID.randomUUID(), Kilograms(1.3e12),
    Asteroid("Beehive", 40.0.km, 23.0.km, 20.0.km), Stars.Sol,
    Independent(Exhumans), Seq.empty,
    Seq.empty) with Orbiting {
    val orbit = ConstantOriginOrbit(0.01, 3000.0.AU, 3.0.º, 32.0.º, 12.0.º, 80.0.º, mass, this.centre.mass); // far far out
    override def extraInfo = Seq(("Population" -> "Unkown"));
  }

  object Evangelium extends Habitat("Evangelium", UUID.randomUUID(), Kilograms(1.3e17),
    Asteroid("Beehive", 120.0.km, 80.0.km, 87.0.km), Stars.Sol,
    Independent(Brinker), Seq(English),
    Seq(Religion, Research)) with Orbiting {
    val orbit = ConstantOriginOrbit(0.01, 3000.0.AU, 2.0.º, 97.0.º, 122.0.º, 60.0.º, mass, this.centre.mass); // far far out
    override def extraInfo = Seq(("Population" -> "400"));
  }

  // Skipping Crystal Wind, Tycho Brahe, Ninjing, and Tyche due to distance p.145 Rinward

  val list: Seq[Habitat] = Seq(
    Aspis, Extropia, Settlements.Ceres.Piazzi.Hab, Legba, NovaYork, Pallas, Starwell, Vesta, Zombieland,
    Amalthea, Settlements.Ganymede.LibertyStation.Hab, TheCastle, TheHolySee, JSFAntimatterFactory,
    Aoede, Aitne, Kale, Taygete, Callirrhoe, Carpo, Euanthe, Helike,
    Locus, CSquat, CasaArturo, CatalHayuk, Exarchia, Lot49, Respect, Turing, Winter,
    Volkograd, Bright, Kiviuq, MeatHab, IZulu, PhelansRecourse, MarseillesPrometheus, KronosCluster.Hab,
    Salah, Epimetheus, Janus, DIYShipyards, LongHaul, NguyensCompact, RedEmmasDance, SmallMajesties,
    Settlements.Titan.CommonwealthHub.Hab, Phoebe, Skathi, Abramsen, Mankell,
    Xiphos,
    Glitch, Mahogany, Afrik, Free, Hawking, Ilmarinen,
    AlphaPlus, NewSarpalius, ThunderOnTheHorizon, Haumea, Langford, Markov, HabitatOnTheRock, Whiskey,
    Tulihaend,
    Sedna, Haven, Thorne,
    Wisdom, TheNest, Evangelium);
}
