package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{ AstronomicalUnits => AU }
import squants.time._
import squants.motion._

object Moons {
    object Luna extends Moon("Luna", UUID.randomUUID(), Kilograms(7.342e22), Kilometers(1737.1), Planets.Earth) with Orbiting {
        val orbit = VariableOrbit(0.0549, Kilometers(384399), Degrees(5.145),
            ShiftingAngle(Degrees(218.31617), Days(6798.3835), Regressive),
            ShiftingAngle(Degrees(0.0), Days(3230.25), Progressive),
            Degrees(358.617), mass, Planets.Earth);
    }

    object Titan extends Moon("Titan", UUID.randomUUID(), Kilograms(1.3452e23), Kilometers(2575.5), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0288, Kilometers(1221870), Degrees(29.56378),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Iapetus extends Moon("Iapetus", UUID.randomUUID(), Kilograms(1.805635e21), Kilometers(734.5), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0286125, Kilometers(3560820), Degrees(17.28),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Rhea extends Moon("Rhea", UUID.randomUUID(), Kilograms(2.306518e21), Kilometers(763.8), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0012583, Kilometers(527108), Degrees(29.56024),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Tethys extends Moon("Tethys", UUID.randomUUID(), Kilograms(6.17449e20), Kilometers(531.1), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0001, Kilometers(294619), Degrees(30.33524),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Enceladus extends Moon("Enceladus", UUID.randomUUID(), Kilograms(1.08022e20), Kilometers(252.1), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0047, Kilometers(237948), Degrees(29.23424),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Dione extends Moon("Dione", UUID.randomUUID(), Kilograms(1.095452e21), Kilometers(561.4), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0022, Kilometers(377396), Degrees(29.23424),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

    object Mimas extends Moon("Mimas", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Saturn) with Orbiting {
        val orbit = VariableOrbit(0.0196, Kilometers(185539), Degrees(30.78924),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Saturn);
    }

//    object L1Test extends Moon("L1Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new L1(Stars.Sol, Planets.Earth);
//    }
//
//    object L2Test extends Moon("L2Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new L2(Stars.Sol, Planets.Earth);
//    }
//
//    object L3Test extends Moon("L3Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new L3(Stars.Sol, Planets.Earth);
//    }
//
//    object L4Test extends Moon("L4Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new L4(Stars.Sol, Planets.Earth);
//    }
//
//    object L5Test extends Moon("L5Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new L5(Stars.Sol, Planets.Earth);
//    }
//
//    object L2LTest extends Moon("L2LTest", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
//        val orbit = new LissajousOrbit(L1Test.orbit, Kilometers(700000.0), Kilometers(500000.0), DegreesPerSecond(0.04086), DegreesPerSecond(0.02015), 3.229, Degrees(0.0), Degrees(0.0));
//    }

    val forPlanet = Map("Earth" -> Seq(Luna),
        "Saturn" -> Seq(Titan, Iapetus, Rhea, Tethys, Enceladus, Dione, Mimas));
    val colours = Map("Luna" -> 0x848381, //"L1Test" -> 0x848381, "L2Test" -> 0x848381, "L3Test" -> 0x848381, "L4Test" -> 0x848381, "L5Test" -> 0x848381, "L2LTest" -> 0x848381,
        "Titan" -> 0xcfc063, "Iapetus" -> 0x848381, "Rhea" -> 0x848381, "Tethys" -> 0x848381, "Enceladus" -> 0x848381, "Dione" -> 0x848381, "Mimas" -> 0x848381);
}