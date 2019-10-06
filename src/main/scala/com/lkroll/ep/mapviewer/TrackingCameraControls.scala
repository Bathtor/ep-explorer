package com.lkroll.ep.mapviewer

import org.denigma.threejs.extensions.animations.{Animation, Scheduler}
import org.denigma.threejs.extensions.controls.{CameraControls}
import org.denigma.threejs.{Camera, Object3D, Scene, Vector3}
import org.denigma.threejs.extras.OrbitControls
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, Event, HTMLElement}
import org.scalajs.dom.MouseEvent

import scala.concurrent.duration
import scala.concurrent.duration.Duration
import scala.concurrent.duration.MILLISECONDS
import scala.language.postfixOps

import com.lkroll.ep.mapviewer.graphics.{GraphicsObject, GraphicsObjects, IntersectionPriority}

import scribe.Logging

abstract class TrackingCameraControls(val camera: Camera,
                                      val element: HTMLElement, //scalastyle:ignore
                                      val scene: Scene,
                                      val width: Double,
                                      val height: Double,
                                      var tracked: GraphicsObject,
                                      val isPriority: IntersectionPriority)
    extends CameraControls
    with IntersectionControls
    with Logging {

  def markLocal(obj: GraphicsObject): Unit;
  def unmarkLocal(obj: GraphicsObject): Unit;

  tracked match {
    case o: graphics.Overlayed => {
      o.overlay.select();
    }
    case _ => // nothing
  }
  markLocal(tracked);
  UI.replaceTracking(tracked.data);

  private val orbitControl = {
    val oc = new OrbitControls(camera, element);
    oc.enableDamping = true;
    oc.dampingFactor = 0.25;
    oc.maxAzimuthAngle = Double.PositiveInfinity;
    oc.minAzimuthAngle = Double.NegativeInfinity;
    oc
  }

  private var lastPosition = tracked.position.clone();

  implicit val scheduler = new Scheduler().start()

  def track(obj: GraphicsObject): Unit = {
    val start = tracked.position.clone();
    val dp = new Vector3().subVectors(obj.position, tracked.position);
    val oldObj = tracked;
    tracked = obj;

    new Animation(Duration(1, duration.SECONDS))(p => {

      val m = dp.clone().multiplyScalar(p)
      val cur = start.clone().add(m)
      // dom.console.info(cur)
      orbitControl.target.copy(cur)
    }).go(scheduler)
    // center.copy(position)
    oldObj match {
      case o: graphics.Overlayed => {
        o.overlay.clear();
      }
      case _ => // nothing
    }
    unmarkLocal(oldObj);
    obj match {
      case o: graphics.Overlayed => {
        o.overlay.select();
      }
      case _ => // nothing
    }
    markLocal(obj);
    UI.replaceTracking(tracked.data)
  }

  override def update() = {
    orbitControl.target.copy(tracked.position);
    val diff = tracked.position.clone().sub(lastPosition);
    camera.position.add(diff);
    orbitControl.update();
    lastPosition.copy(tracked.position);
  }

  override def onMouseDown(event: MouseEvent) {
    orbitControl.onMouseDown(event);
  }
  override def onMouseMove(event: MouseEvent) = {
    this.onCursorMove(event.clientX, event.clientY);
    //orbitControl.onMouseMove(event);
  }
  override def onMouseUp(event: MouseEvent) {
    //orbitControl.onMouseUp(event);
  }
  override def onMouseWheel(event: MouseEvent) {
    //orbitControl.onMouseWheel(event);
  }

  override def enabled = true;

  def onDoubleClick(event: MouseEvent): Unit = {
    val objO = isPriority.prioritiseIntersection(this.intersections);
    objO match {
      case Some(obj) =>
        obj match {
          case o if (o.id == tracked.id) => dom.console.info(s"Already tracking ${o.name}")
          case o                         => track(o)
        }
      case None => dom.console.info("Nothing intersected with click!") // nothing
    }
  }

  def attach(el: Element) {
    el.addEventListener("dblclick", (this.onDoubleClick _).asInstanceOf[Function[Event, _]], false)
    el.addEventListener("mousemove", (this.onMouseMove _).asInstanceOf[Function[Event, _]], false)
  }
  this.attach(element);
}
