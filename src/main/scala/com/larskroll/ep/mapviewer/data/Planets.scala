package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{ AstronomicalUnits => AU }

object Planets {

    object Earth extends Planet("Earth", UUID.randomUUID(), Kilograms(5.97237e24), Kilometers(6371.0)) with Orbiting {
        val orbit = ConstantOrbit(0.0167086, AU(1.000001018), Degrees(0.00005), Degrees(-11.26064), Degrees(114.20783), Degrees(358.617), mass, Stars.Sol.mass);
    }

    object Mars extends Planet("Mars", UUID.randomUUID(), Kilograms(0.64171e24), Kilometers(3389.5)) with Orbiting {
        val orbit = ConstantOrbit(0.0934, AU(1.523679), Degrees(1.850), Degrees(49.558), Degrees(286.502), Degrees(19.373), mass, Stars.Sol.mass);
    }

    object Mercury extends Planet("Mercury", UUID.randomUUID(), Kilograms(0.33011e24), Kilometers(2439.7)) with Orbiting {
        val orbit = ConstantOrbit(0.205630, AU(0.387098), Degrees(7.005), Degrees(48.331), Degrees(29.124), Degrees(174.796), mass, Stars.Sol.mass);
    }

    object Venus extends Planet("Venus", UUID.randomUUID(), Kilograms(4.8675e24), Kilometers(6051.8)) with Orbiting {
        val orbit = ConstantOrbit(0.006772, AU(0.723332), Degrees(3.39458), Degrees(76.680), Degrees(54.884), Degrees(50.115), mass, Stars.Sol.mass);
    }

    object Jupiter extends Planet("Jupiter", UUID.randomUUID(), Kilograms(1.8986e27), Kilometers(69911)) with Orbiting {
        val orbit = ConstantOrbit(0.048498, AU(5.20260), Degrees(1.303), Degrees(100.464), Degrees(273.867), Degrees(20.020), mass, Stars.Sol.mass);
    }

    object Saturn extends Planet("Saturn", UUID.randomUUID(), Kilograms(5.6836e26), Kilometers(58232)) with Orbiting {
        val orbit = ConstantOrbit(0.05555, AU(9.554909), Degrees(2.485240), Degrees(113.665), Degrees(339.392), Degrees(317.020), mass, Stars.Sol.mass);
    }

    object Uranus extends Planet("Uranus", UUID.randomUUID(), Kilograms(8.6810e25), Kilometers(25362)) with Orbiting {
        val orbit = ConstantOrbit(0.046381, AU(19.2184), Degrees(0.773), Degrees(74.006), Degrees(96.998857), Degrees(142.2386), mass, Stars.Sol.mass);
    }

    object Neptune extends Planet("Neptune", UUID.randomUUID(), Kilograms(1.0243e26), Kilometers(24622)) with Orbiting {
        val orbit = ConstantOrbit(0.009456, AU(30.110387), Degrees(1.767975), Degrees(131.784), Degrees(276.336), Degrees(256.228), mass, Stars.Sol.mass);
    }

    val list = Seq(Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune);
    val colours = Map("Mercury" -> 0x848381, "Venus" -> 0xbeb977, "Earth" -> 0x838ab6,
        "Mars" -> 0xdbc490, "Jupiter" -> 0xf5f2d3, "Saturn" -> 0xe9edcc,
        "Uranus" -> 0xadc8d3, "Neptune" -> 0xa0b7d7);
}