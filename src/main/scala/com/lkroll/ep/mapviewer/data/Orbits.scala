package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

object SunEarthL1 extends L1(Stars.Sol, Planets.Earth)

object EarthLunaL4 extends L4(Planets.Earth, Moons.Luna)
object EarthLunaL5 extends L5(Planets.Earth, Moons.Luna)
object MarsPhobosL4 extends L4(Planets.Mars, Moons.Phobos)
object MarsPhobosL5 extends L5(Planets.Mars, Moons.Phobos)
object SolMarsL2 extends L2(Stars.Sol, Planets.Mars)
object SolMarsL4 extends L4(Stars.Sol, Planets.Mars)
object SolMarsL5 extends L5(Stars.Sol, Planets.Mars)
object SolEarthL3 extends L3(Stars.Sol, Planets.Earth)
object JovianTrojans extends L5(Stars.Sol, Planets.Jupiter)
object JovianGreeks extends L4(Stars.Sol, Planets.Jupiter)
