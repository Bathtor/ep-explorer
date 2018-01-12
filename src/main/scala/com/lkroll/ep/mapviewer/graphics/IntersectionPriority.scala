package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs.{ Object3D, Intersection }

trait IntersectionPriority {
  def prioritiseIntersection(intersections: List[Intersection]): Option[GraphicsObject] = {
    val sorted = intersections.sortWith((a, b) => a.distance < b.distance);
    prioritiseObject(sorted.map { i => i.`object` })
  }

  def prioritiseObject(objects: List[Object3D]): Option[GraphicsObject];
}

object IntersectionPriorities {

  object FirstLargest extends IntersectionPriority {
    override def prioritiseObject(objects: List[Object3D]): Option[GraphicsObject] = {
      import scala.util.control.Breaks._
      var firstMoon: Option[Moon] = None;
      var firstPlanet: Option[PlanetObject] = None;
      var sun: Option[Star] = None;
      var other: Option[GraphicsObject] = None;
      breakable {
        for (mesh <- objects) {
          val objO = GraphicsObjects(mesh.id);
          objO match {
            case Some(star: Star) => {
              sun = Some(star);
              break // if we are intersecting the sun
            }
            case Some(planet: PlanetObject) => {
              if (firstPlanet.isDefined) {
                break // as soon as we find the second planet we are done
              } else {
                firstPlanet = Some(planet)
              }
            }
            case Some(moon: Moon) => {
              if (!firstMoon.isDefined) {
                firstMoon = Some(moon)
              }
            }
            case Some(x) => if (!other.isDefined) {
              other = Some(x)
            }
            case None => // ignore
          }
        }
      }
      val order = Seq(sun, firstPlanet, firstMoon, other);
      order.find(_.isDefined) match {
        case Some(o) => o
        case None    => None
      }
    }
  }

  object FirstSmallest extends IntersectionPriority {
    override def prioritiseObject(objects: List[Object3D]): Option[GraphicsObject] = {
      var firstMoon: Option[Moon] = None;
      var moonSingle: Option[MoonSingle] = None;
      var firstPlanet: Option[Planet] = None;
      var planetSingle: Option[PlanetSingle] = None;
      var sun: Option[Star] = None;
      var other: Option[GraphicsObject] = None;
      for (mesh <- objects) {
        val objO = GraphicsObjects(mesh.id);
        objO match {
          case Some(star: Star) => {
            sun = Some(star);
          }
          case Some(planet: Planet) => {
            if (!firstPlanet.isDefined) {
              firstPlanet = Some(planet);
            }
          }
          case Some(planet: PlanetSingle) => {
            if (!planetSingle.isDefined) {
              planetSingle = Some(planet);
            }
          }
          case Some(moon: Moon) => {
            if (!firstMoon.isDefined) {
              firstMoon = Some(moon);
            }
          }
          case Some(moon: MoonSingle) => {
            if (!moonSingle.isDefined) {
              moonSingle = Some(moon);
            }
          }
          case Some(x) => {
            if (!other.isDefined) {
              other = Some(x);
            }
          }
          case None => // ignore
        }
      }
      val order = Seq(other, firstMoon, firstPlanet, moonSingle, planetSingle, sun);
      order.find(_.isDefined) match {
        case Some(o) => o
        case None    => None
      }
    }
  }
}
