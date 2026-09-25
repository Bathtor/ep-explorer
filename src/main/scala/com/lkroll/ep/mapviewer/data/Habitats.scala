package com.lkroll.ep.mapviewer.data

import com.lkroll.ep.mapviewer.datamodel._

object Habitats {

  import ExtraUnits._;

  val list: Seq[Habitat] = HabitatsSunward.list ++ HabitatsRimward.list;
}
