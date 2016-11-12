package com.larskroll.ep.mapviewer.data

import squants._
import squants.space._
import java.util.UUID

import org.denigma.threejs.Vector3

object Stars {
    object Sol extends Star("Sol", UUID.randomUUID(), Kilograms(1.98855e30), Kilometers(695700)) with Orbiting {
        val orbit: Orbit = StaticOrbit(new Vector3(0.0, 0.0, 0.0))
    }
}