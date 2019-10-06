package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{AstronomicalUnits => AU}
import squants.time._

object Planets {
  import ExtraUnits._;

  object Earth
      extends Planet("Earth", UUID.randomUUID(), Kilograms(5.97237e24), Kilometers(6371.0))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.01671022,
                                    1.00000011.AU,
                                    0.00005.º,
                                    348.73936.º,
                                    114.20783.º,
                                    357.05281.º,
                                    mass,
                                    Stars.Sol.mass);
    val rotation = EarthEquatorConstantRotation;
  }

  object Mars
      extends Planet("Mars", UUID.randomUUID(), Kilograms(0.64171e24), Kilometers(3389.5))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.09341233,
                                    AU(1.52366231),
                                    Degrees(1.85061),
                                    Degrees(49.57854),
                                    Degrees(286.4623),
                                    Degrees(19.41248),
                                    mass,
                                    Stars.Sol.mass);
    //val rotation = EquatorialConstantRotation(DegreesPerDay(350.89198226), Degrees(317.68143), Degrees(0.0), Degrees(176.630));
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(350.89198226), Degrees(317.68143), Degrees(52.88650), Degrees(176.630));
  }

  object Mercury
      extends Planet("Mercury", UUID.randomUUID(), Kilograms(0.33011e24), Kilometers(2439.7))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.20563069,
                                    AU(0.38709893),
                                    Degrees(7.00487),
                                    Degrees(48.33167),
                                    Degrees(29.12478),
                                    Degrees(174.79439),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(6.1385025), Degrees(281.0097), Degrees(61.4143), Degrees(329.5469));
  }

  object Venus
      extends Planet("Venus", UUID.randomUUID(), Kilograms(4.8675e24), Kilometers(6051.8))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.00677323,
                                    AU(0.72333199),
                                    Degrees(3.39471),
                                    Degrees(76.68069),
                                    Degrees(54.85229),
                                    Degrees(50.44675),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(1.4813688), Degrees(272.76), Degrees(67.16), Degrees(160.20), true);
  }

  object Ceres extends Planet("Ceres", UUID.randomUUID(), 9.393e20.kg, 473.0.km) with Orbiting with Rotating {
    val orbit = ConstantOriginOrbit(
      0.075823,
      2.7675.AU,
      10.593.º,
      80.3293.º,
      72.5220.º,
      95.9891.º,
      mass,
      Stars.Sol.mass
    ); // note: the mean anomaly is from the wrong epoch... I was too lazy to look up the right one
    val rotation = EquatorialConstantRotation(952.1532.ºd, 291.0.º, 59.0.º, 170.90.º);
  }

  object Jupiter
      extends Planet("Jupiter", UUID.randomUUID(), Kilograms(1.8986e27), Kilometers(69911))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.04839266,
                                    AU(5.20336301),
                                    Degrees(1.30530),
                                    Degrees(100.55615),
                                    Degrees(274.1977),
                                    Degrees(19.65053),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(870.5360000), Degrees(268.056595), Degrees(64.495303), Degrees(284.95));
  }

  object Saturn
      extends Planet("Saturn", UUID.randomUUID(), Kilograms(5.6836e26), Kilometers(58232))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.05415060,
                                    AU(9.53707032),
                                    Degrees(2.48446),
                                    Degrees(113.71504),
                                    Degrees(338.7169),
                                    Degrees(317.51238),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(810.7939024), Degrees(40.589), Degrees(83.537), Degrees(38.90));
  }

  object Uranus
      extends Planet("Uranus", UUID.randomUUID(), Kilograms(8.6810e25), Kilometers(25362))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.04716771,
                                    AU(19.19126393),
                                    Degrees(0.76986),
                                    Degrees(74.22988),
                                    Degrees(96.73436),
                                    Degrees(142.26794),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(501.1600928), Degrees(257.311), Degrees(-15.175), Degrees(203.81));
  }

  object Neptune
      extends Planet("Neptune", UUID.randomUUID(), Kilograms(1.0243e26), Kilometers(24622))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.00858587,
                                    AU(30.06896348),
                                    Degrees(1.76917),
                                    Degrees(131.72169),
                                    Degrees(273.24966),
                                    Degrees(259.90868),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(536.3128492), Degrees(299.36), Degrees(43.46), Degrees(253.18));
  }

  object Pluto
      extends Planet("Pluto", UUID.randomUUID(), Kilograms(1.303e22), Kilometers(1187))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.24880766,
                                    AU(39.48168677),
                                    Degrees(17.14175),
                                    Degrees(110.30347),
                                    Degrees(113.76329),
                                    Degrees(14.86205),
                                    mass,
                                    Stars.Sol.mass);
    val rotation =
      EquatorialConstantRotation(DegreesPerDay(56.3625225), Degrees(132.993), Degrees(-6.163), Degrees(237.305));
  }

  object Eris
      extends Planet("Eris", UUID.randomUUID(), Kilograms(1.64e22), Kilometers(1163))
      with Orbiting
      with Rotating {
    val orbit = ConstantOriginOrbit(0.44068,
                                    AU(67.781),
                                    Degrees(44.0445),
                                    Degrees(35.9531),
                                    Degrees(150.977),
                                    Degrees(204.16),
                                    mass,
                                    Stars.Sol.mass);
    val rotation = EquatorialConstantRotation(RotationPeriod(Hours(25.92)), Degrees(0.0), Degrees(0.0), Degrees(0.0)); // other values are unkown (http://www.johnstonsarchive.net/astro/astmoons/am-136199.html)
  }

  val list = Seq(Mercury, Venus, Earth, Mars, Ceres, Jupiter, Saturn, Uranus, Neptune, Pluto, Eris);
  //    val colours = Map("Mercury" -> 0x848381, "Venus" -> 0xbeb977, "Earth" -> 0x838ab6,
  //        "Mars" -> 0xdbc490, "Jupiter" -> 0xf5f2d3, "Saturn" -> 0xe9edcc,
  //        "Uranus" -> 0xadc8d3, "Neptune" -> 0xa0b7d7, "Pluto" -> 0xdbc490,
  //        "Eris" -> 0x848381);
}
