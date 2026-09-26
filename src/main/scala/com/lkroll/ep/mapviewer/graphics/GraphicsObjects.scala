package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.three.Object3D

object GraphicsObjects {
  private val id2obj = scala.collection.mutable.Map.empty[Double, GraphicsObject];

  def put(obj3d: Object3D, obj: GraphicsObject): Unit = {
    id2obj += (obj3d.id -> obj)
  }

  def apply(id: Double): Option[GraphicsObject] = {
    id2obj.get(id);
  }

}
