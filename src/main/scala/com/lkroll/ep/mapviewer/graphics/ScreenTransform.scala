package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._

case class ScreenTransform(val width: Double, val height: Double,
                           val left: Double = 0.0, val top: Double = 0.0) {

  def toNormalizedCameraSpace(in: Vector2): Vector2 = {
    val v = new Vector2();
    v.setX(2 * ((in.x - left) / width) - 1);
    v.setY(-2 * ((in.y - top) / height) + 1);
    v
  }

  def toScreenSpace(in: Vector2): Vector2 = {
    val v = new Vector2();
    v.setX(((in.x + 1) / 2) * width + left);
    v.setY(((1 - in.y) / 2) * height + top);
    v
  }

  def toScreenSpace(in: Vector3): Vector2 = {
    val v = new Vector2();
    v.setX(((in.x + 1) / 2) * width + left);
    v.setY(((1 - in.y) / 2) * height + top);
    v
  }

}