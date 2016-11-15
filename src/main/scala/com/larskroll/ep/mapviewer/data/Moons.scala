package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{ AstronomicalUnits => AU }
import squants.time._
import squants.motion._

object Moons {
    // Earth
    object Luna extends Moon("Luna", UUID.randomUUID(), Kilograms(7.342e22), Kilometers(1737.1), Planets.Earth) with Orbiting {
        val orbit = VariableOrbit(0.0549, Kilometers(384399), Degrees(5.145),
            ShiftingAngle(Degrees(218.31617), Days(6798.3835), Regressive),
            ShiftingAngle(Degrees(0.0), Days(3230.25), Progressive),
            Degrees(358.617), mass, Planets.Earth);
    }

    // Mars
    object Phobos extends Moon("Phobos", UUID.randomUUID(), Kilograms(1.0659e16), Kilometers(11.2667), Planets.Mars) with Orbiting {
        val orbit = VariableOrbit(0.0151, Kilometers(9376), Degrees(26.04),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Mars);
    }
    object Deimos extends Moon("Deimos", UUID.randomUUID(), Kilograms(1.4762e15), Kilometers(6.2), Planets.Mars) with Orbiting {
        val orbit = VariableOrbit(0.00033, Kilometers(23463.2), Degrees(27.58),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Mars);
    }

    // Jupiter
    object Io extends Moon("Io", UUID.randomUUID(), Kilograms(8.931938e22), Kilometers(1821.6), Planets.Jupiter) with Orbiting {
        val orbit = VariableOrbit(0.0041, Kilometers(421700), Degrees(2.213),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Jupiter);
    }

    object Europa extends Moon("Europa", UUID.randomUUID(), Kilograms(4.799844e22), Kilometers(1560.8), Planets.Jupiter) with Orbiting {
        val orbit = VariableOrbit(0.009, Kilometers(670900), Degrees(1.791),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Jupiter);
    }

    object Ganymede extends Moon("Ganymede", UUID.randomUUID(), Kilograms(1.4819e23), Kilometers(2634.1), Planets.Jupiter) with Orbiting {
        val orbit = VariableOrbit(0.0013, Kilometers(1070400), Degrees(2.214),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Jupiter);
    }

    object Callisto extends Moon("Callisto", UUID.randomUUID(), Kilograms(1.075938e23), Kilometers(2410.3), Planets.Jupiter) with Orbiting {
        val orbit = VariableOrbit(0.0074, Kilometers(1882700), Degrees(2.017),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Jupiter);
    }

    // Saturn
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

    // Uranus
    object Miranda extends Moon("Miranda", UUID.randomUUID(), Kilograms(6.59e19), Kilometers(235.8), Planets.Uranus) with Orbiting {
        val orbit = VariableOrbit(0.0013, Kilometers(129390), Degrees(102.002),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Uranus);
    }
    object Ariel extends Moon("Ariel", UUID.randomUUID(), Kilograms(1.353e21), Kilometers(578.9), Planets.Uranus) with Orbiting {
        val orbit = VariableOrbit(0.0012, Kilometers(191020), Degrees(98.03),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Uranus);
    }
    object Umbriel extends Moon("Umbriel", UUID.randomUUID(), Kilograms(1.172e21), Kilometers(584.7), Planets.Uranus) with Orbiting {
        val orbit = VariableOrbit(0.0039, Kilometers(266000), Degrees(97.898),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Uranus);
    }
    object Titania extends Moon("Titania", UUID.randomUUID(), Kilograms(3.527e21), Kilometers(788.4), Planets.Uranus) with Orbiting {
        val orbit = VariableOrbit(0.0011, Kilometers(435910), Degrees(98.11),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Uranus);
    }
    object Oberon extends Moon("Oberon", UUID.randomUUID(), Kilograms(3.014e21), Kilometers(761.4), Planets.Uranus) with Orbiting {
        val orbit = VariableOrbit(0.0014, Kilometers(583520), Degrees(97.828),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Uranus);
    }

    // Neptune
    object Nereid extends Moon("Nereid", UUID.randomUUID(), Kilograms(3.09e19), Kilometers(170), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.7507, Kilometers(5513787), Degrees(62.637975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Triton extends Moon("Triton", UUID.randomUUID(), Kilograms(2.14e22), Kilometers(1353.4), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.000016, Kilometers(354759), Degrees(129.812),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune, true);
    }

    object Proteus extends Moon("Proteus", UUID.randomUUID(), Kilograms(4.4e19), Kilometers(21), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.00053, Kilometers(117647), Degrees(30.607975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Naiad extends Moon("Naiad", UUID.randomUUID(), Kilograms(1.9e17), Kilometers(33), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.0004, Kilometers(48227), Degrees(34.837975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Thalassa extends Moon("Talassa", UUID.randomUUID(), Kilograms(3.5e17), Kilometers(41), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.0002, Kilometers(50075), Degrees(30.297975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Despina extends Moon("Despina", UUID.randomUUID(), Kilograms(2.2e18), Kilometers(75), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.0002, Kilometers(52526), Degrees(30.303975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Galatea extends Moon("Galatea", UUID.randomUUID(), Kilograms(2.12e18), Kilometers(88), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.00004, Kilometers(61953), Degrees(30.139975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    object Larissa extends Moon("Larissa", UUID.randomUUID(), Kilograms(4.2e18), Kilometers(97), Planets.Neptune) with Orbiting {
        val orbit = VariableOrbit(0.001393, Kilometers(73548), Degrees(30.338975),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Neptune);
    }

    // Pluto
    object Charon extends Moon("Charon", UUID.randomUUID(), Kilograms(1.586e21), Kilometers(606), Planets.Pluto) with Orbiting {
        // FIXME Pluto-Charon is more of a binary system, so this modelling is actually wrong
        val orbit = VariableOrbit(0.0, Kilometers(19571), Degrees(112.783),
            ConstantAngle(Degrees(223.046)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Pluto);
    }
    object Styx extends Moon("Styx", UUID.randomUUID(), Kilograms(7.5e15), Kilometers(9), Planets.Pluto) with Orbiting {
        val orbit = VariableOrbit(0.005787, Kilometers(42656), Degrees(140.499),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Pluto);
    }
    object Nix extends Moon("Nix", UUID.randomUUID(), Kilograms(4.5e16), Kilometers(35), Planets.Pluto) with Orbiting {
        val orbit = VariableOrbit(0.0, Kilometers(48694), Degrees(139.823),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Pluto);
    }
    object Kerberos extends Moon("Kerberos", UUID.randomUUID(), Kilograms(1.65e16), Kilometers(10), Planets.Pluto) with Orbiting {
        val orbit = VariableOrbit(0.00328, Kilometers(57783), Degrees(140.079),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Pluto);
    }
    object Hydra extends Moon("Hydra", UUID.randomUUID(), Kilograms(4.8e16), Kilometers(45), Planets.Pluto) with Orbiting {
        val orbit = VariableOrbit(0.005862, Kilometers(64738), Degrees(139.932),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Pluto);
    }

    // Eris
    object Dysnomia extends Moon("Dysnomia", UUID.randomUUID(), Kilograms(2.0e20), Kilometers(342), Planets.Eris) with Orbiting {
        // Note that the article that calculated Eris' mass assumed that Dysnomia's mass in the system is negligable, but I needed a value
        // since they they gave +- 0.02e22 as error bars I simply transfered 0.02e22 away from Eris to Dysnomia to make the simulation work.
        // This is however most likely too large...
        // see http://science.sciencemag.org/content/316/5831/1585 for details
        val orbit = VariableOrbit(0.013, Kilometers(37350), Degrees(142),
            ConstantAngle(Degrees(0.0)),
            ConstantAngle(Degrees(0.0)),
            Degrees(0.0), mass, Planets.Eris);
    }

    //        object L1Test extends Moon("L1Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //            val orbit = new L1(Planets.Earth, Moons.Luna);
    //        }
    //    
    //        object L2Test extends Moon("L2Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //            val orbit = new L2(Planets.Earth, Moons.Luna);
    //        }
    //    
    //        object L3Test extends Moon("L3Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //            val orbit = new L3(Planets.Earth, Moons.Luna);
    //        }
    //    
    //        object L4Test extends Moon("L4Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //            val orbit = new L4(Planets.Earth, Moons.Luna);
    //        }
    //    
    //        object L5Test extends Moon("L5Test", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //            //val orbit = new L5(Stars.Sol, Planets.Earth);
    //            val orbit = new L5(Planets.Earth, Moons.Luna);
    //        }
    //
    //    object L2LTest extends Moon("L2LTest", UUID.randomUUID(), Kilograms(3.7493e19), Kilometers(198.2), Planets.Earth) with Orbiting {
    //        val orbit = new LissajousOrbit(L1Test.orbit, Kilometers(700000.0), Kilometers(500000.0), DegreesPerSecond(0.04086), DegreesPerSecond(0.02015), 3.229, Degrees(0.0), Degrees(0.0));
    //    }

    val forPlanet = Map(Planets.Earth.name -> Seq(Luna), //, L1Test, L2Test, L3Test, L4Test, L5Test),
        Planets.Mars.name -> Seq(Phobos, Deimos),
        Planets.Jupiter.name -> Seq(Io, Europa, Ganymede, Callisto),
        Planets.Saturn.name -> Seq(Titan, Iapetus, Rhea, Tethys, Enceladus, Dione, Mimas),
        Planets.Uranus.name -> Seq(Miranda, Ariel, Umbriel, Titania, Oberon),
        Planets.Neptune.name -> Seq(Nereid, Triton, Proteus, Naiad, Thalassa, Despina, Galatea),
        Planets.Pluto.name -> Seq(Charon, Styx, Nix, Kerberos, Hydra),
        Planets.Eris.name -> Seq(Dysnomia));
    val colours = Map(Luna.name -> 0x848381, //, "L1Test" -> 0x848381, "L2Test" -> 0x848381, "L3Test" -> 0x848381, "L4Test" -> 0x848381, "L5Test" -> 0x848381, "L2LTest" -> 0x848381,
        Phobos.name -> 0x848381, Deimos.name -> 0x848381,
        Io.name -> 0xcfc063, Europa.name -> 0x848381, Ganymede.name -> 0x848381, Callisto.name -> 0x848381,
        Titan.name -> 0xcfc063, Iapetus.name -> 0x848381, Rhea.name -> 0x848381, Tethys.name -> 0x848381, Enceladus.name -> 0x848381, Dione.name -> 0x848381, Mimas.name -> 0x848381,
        Miranda.name -> 0x848381, Ariel.name -> 0x848381, Umbriel.name -> 0x646361, Titania.name -> 0x848381, Oberon.name -> 0x848381,
        Nereid.name -> 0x848381, Triton.name -> 0x848381, Proteus.name -> 0x848381, Naiad.name -> 0x848381, Thalassa.name -> 0x848381, Despina.name -> 0x848381, Galatea.name -> 0x848381,
        Charon.name -> 0x848381, Styx.name -> 0x848381, Nix.name -> 0x848381, Kerberos.name -> 0x848381, Hydra.name -> 0x848381,
        Dysnomia.name -> 0x848381);
}