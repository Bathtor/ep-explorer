package com.larskroll.ep.mapviewer.data

import squants._
import squants.space._
import java.util.UUID

import org.denigma.threejs.Vector3

object Stars {
    object Sol extends Star("Sol", UUID.randomUUID(), Kilograms(1.98855e30), Kilometers(695700), Kelvin(5772.0)) with Orbiting with Rotating {
        val orbit: Orbit = StaticOrbit(new Vector3(0.0, 0.0, 0.0));
        val rotation = EquatorialConstantRotation(DegreesPerDay(14.1844000), Degrees(286.13), Degrees(63.87), Degrees(84.176));
    }
}