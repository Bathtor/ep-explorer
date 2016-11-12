package com.larskroll.ep.mapviewer.graphics

import org.denigma.threejs.{ Object3D, Intersection }

object GraphicsObjects {
    private val id2obj = scala.collection.mutable.Map.empty[Double, GraphicsObject];

    def put(obj3d: Object3D, obj: GraphicsObject) {
        id2obj += (obj3d.id -> obj)
    }

    def apply(id: Double): Option[GraphicsObject] = {
        id2obj.get(id);
    }

    def prioritiseIntersection(intersections: Iterable[Intersection]): Option[GraphicsObject] = {
        this.prioritiseIntersection(intersections.view.map { i => i.`object` })
    }

    def prioritiseIntersection(objects: Iterable[Object3D])(implicit d: DummyImplicit): Option[GraphicsObject] = {
        import scala.util.control.Breaks._
        var firstMoon: Option[Moon] = None;
        var firstPlanet: Option[Planet] = None;
        var sun: Option[Star] = None;
        breakable {
            for (mesh <- objects) {
                val objO = id2obj.get(mesh.id);
                objO match {
                    case Some(star: Star) => {
                        sun = Some(star);
                        break // if we are intersecting the sun
                    }
                    case Some(planet: Planet) => {
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
                    case _ => // ignore
                }
            }
        }
        if (sun.isDefined) {
            sun
        } else if (firstPlanet.isDefined) {
            firstPlanet
        } else if (firstMoon.isDefined) {
            firstMoon
        } else {
            None
        }
    }
}