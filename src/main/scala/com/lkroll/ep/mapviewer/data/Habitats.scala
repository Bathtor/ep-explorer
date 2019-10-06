package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

import java.util.UUID;
import squants._
import squants.space._
import squants.space.{AstronomicalUnits => AU}
import squants.time._
import squants.motion._

import Languages._
import Polities.{PlanetaryConsortium => _, _}
import Corps._
import Industries._

object Habitats {

  import ExtraUnits._;

  val list: Seq[Habitat] = HabitatsSunward.list ++ HabitatsRimward.list;
}
