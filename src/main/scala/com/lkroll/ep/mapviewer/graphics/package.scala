package com.lkroll.ep.mapviewer

import org.denigma.threejs.Vector3

package object graphics {

  sealed trait ViewType
  object SingleView extends ViewType
  object SystemView extends ViewType

  def objectForData(targetData: datamodel.AstronomicalObject): GraphicsObject = {
    targetData match {
      case star: datamodel.Star => {
        Star.fromStarData(star, SingleView)
      }
      case planet: datamodel.Planet => {
        Planet.fromData(planet, SingleView) match {
          case Left(p)  => p
          case Right(p) => p
        }
      }
      case moon: datamodel.Moon => {
        Moon.fromData(moon, SingleView) match {
          case Left(m)  => m
          case Right(m) => m
        }
      }
      case x => {
        throw new RuntimeException(s"Can't render object of type ${x}")
      }
    }
  }

  val vYup = new Vector3(0.0, 1.0, 0.0);
}
