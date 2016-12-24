package com.larskroll.ep.mapviewer

import org.denigma.threejs._
import org.scalajs.dom.MouseEvent
import org.denigma.threejs.extensions.controls.{ CameraControls, JumpCameraControls }
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import graphics._

import com.outr.scribe.Logging

class MapControls(_cam: Camera, _el: HTMLElement, sc: SceneContainer,
                  _width: Double, _height: Double, _tracked: graphics.GraphicsObject)
    extends TrackingCameraControls(_cam, _el, sc.scene, _width, _height, _tracked) with Logging {

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
    val objO = GraphicsObjects.prioritiseIntersection(this.intersections);
    objO match {
      case Some(obj) => select(obj)
      case None      => // whatever
    }
  }

  //    override def onMouseMove(event: MouseEvent) = {
  //        val (offsetX, offsetY) = ($el.offset().left, $el.offset().top)
  //        this.onCursorMove(event.clientX - offsetX, event.clientY - offsetY, width, height)
  //
  ////        enter.keys.foreach {
  ////            case m: Mesh =>
  ////                m.material match {
  ////                    case mat: MeshLambertMaterial => mat.wireframe = true
  ////                    case _                        => // do nothing
  ////                }
  ////
  ////            case _ => // do nothing
  ////        }
  ////
  ////        exit.keys.foreach {
  ////            case m: Mesh =>
  ////                m.material match {
  ////                    case mat: MeshLambertMaterial => mat.wireframe = false
  ////                    case _                        => // do nothing
  ////                }
  ////
  ////            case _ => // do nothing
  ////        }
  //
  //        //rotateOnMove(event)
  //        super.onMouseMove(event);
  //
  //    }

}
