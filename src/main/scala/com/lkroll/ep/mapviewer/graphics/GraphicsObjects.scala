package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs.{ Object3D, Intersection }

object GraphicsObjects {
  private val id2obj = scala.collection.mutable.Map.empty[Double, GraphicsObject];

  def put(obj3d: Object3D, obj: GraphicsObject) {
    id2obj += (obj3d.id -> obj)
  }

  def apply(id: Double): Option[GraphicsObject] = {
    id2obj.get(id);
  }

}
