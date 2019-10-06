package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.datamodel._
import squants.motion._
import squants.time._
import squants.space._

package object data {
  def findObjectForName(name: String): Option[AstronomicalObject] = {
    if (Stars.Sol.name.equalsIgnoreCase(name)) {
      return Some(Stars.Sol);
    }
    Planets.list.foreach { p =>
      if (p.name.equalsIgnoreCase(name)) {
        return Some(p);
      }
    }
    Moons.forPlanet.flatMap(_._2).foreach { m =>
      if (m.name.equalsIgnoreCase(name)) {
        return Some(m);
      }
    }
    Habitats.list.foreach(
      h =>
        if (h.name.equalsIgnoreCase(name)) {
          return Some(h)
        }
    )
    Settlements.forPlanet
      .flatMap(_._2)
      .foreach(
        s =>
          if (s.name.equalsIgnoreCase(name)) {
            return Some(s)
          }
      )
    Settlements.forMoon
      .flatMap(_._2)
      .foreach(
        s =>
          if (s.name.equalsIgnoreCase(name)) {
            return Some(s)
          }
      )
    return None;
  }
}
