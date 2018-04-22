package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs.Vector3

import com.lkroll.ep.mapviewer.SceneContainer
import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject

import squants.Time

trait GraphicsObject {
  def moveTo(pos: Vector3);

  def addToScene(scene: SceneContainer);

  def update(time: Time);

  def children: List[GraphicsObject];

  def name: String;

  def position: Vector3;

  def id: Double;

  def data: Option[AstronomicalObject] = None;

  def boundingRadius: Double;

  def represents(ao: AstronomicalObject): Boolean = {
    data match {
      case Some(d) => ao.id == d.id
      case None    => false
    }
  }
}
