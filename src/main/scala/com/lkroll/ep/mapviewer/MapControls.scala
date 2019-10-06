package com.lkroll.ep.mapviewer

import org.denigma.threejs._
import org.scalajs.dom.MouseEvent
import org.denigma.threejs.extensions.controls.{CameraControls, JumpCameraControls}
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import graphics._

import scribe.Logging

class MapControls(_cam: Camera,
                  _el: HTMLElement,
                  sc: SceneContainer,
                  _width: Double,
                  _height: Double,
                  _tracked: graphics.GraphicsObject,
                  _isPriority: IntersectionPriority)
    extends TrackingCameraControls(_cam, _el, sc.scene, _width, _height, _tracked, _isPriority)
    with Logging {

  override def markLocal(obj: GraphicsObject): Unit = {
    sc.markLocal(obj);
  }
  override def unmarkLocal(obj: GraphicsObject): Unit = {
    sc.unmarkLocal(obj);
  }

  override def sceneObjects: Array[Object3D] = sc.sceneObjects.toArray

  override def overlayObjects: Array[Object3D] = sc.overlayObjects.toArray

  private var topObject: Double = 0.0;

  def select(obj: GraphicsObject) {
    if (topObject != obj.id) {
      val oldObj = GraphicsObjects(topObject);
      oldObj match {
        case Some(obj: Overlayed) => {
          if (obj.id == tracked.id) {
            obj.overlay.select();
          } else {
            obj.overlay.clear();
          }
        }
        case _ => // nothing
      }
      topObject = obj.id;
      obj match {
        case o: Overlayed => {
          o.overlay.hover();
        }
        case x => logger.info(s"Intersected object was not Overlayed: ${x.name}") // nothing
      }
      UI.replaceInfo(obj.data)
    }
  }

  override def onMouseMove(event: MouseEvent) = {
    this.onCursorMove(event.clientX, event.clientY);
    val objO = isPriority.prioritiseIntersection(this.intersections);
    objO match {
      case Some(obj) => select(obj)
      case None      => // whatever
    }
  }

}
