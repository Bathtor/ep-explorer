package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.graphics.{GraphicsObject, IntersectionPriority}
import com.lkroll.ep.mapviewer.three.{Camera, OrbitControls, Scene, Vector3}

import scala.collection.mutable

import org.scalajs.dom
import org.scalajs.dom.{Element, Event, HTMLElement, KeyboardEvent, MouseEvent}
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

  def markAll(): Unit;
  def unmarkAll(): Unit;
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
    // Modern OrbitControls requires an explicit keyboard listener; this element receives focus on mousedown.
    oc.listenToKeyEvents(element);
    oc
  }

  private val lastTargetPosition = tracked.position.clone();

  private val trackingTransitionMillis = 1000.0
  private var trackingTransition: Option[(Vector3, Double)] = None

  object ShowAllPaths extends UndoableAction {
    override def perform(): Unit = {
      if (ToggleAllPaths.on) {
        hide();
      } else {
        show();
      }
    }
    override def undo(): Unit = {
      if (ToggleAllPaths.on) {
        show();
      } else {
        hide();
      }
    }

    private def show(): Unit = {
      markAll();
    }
    private def hide(): Unit = {
      unmarkAll();
      markLocal(tracked);
    }
  }

  object ToggleAllPaths extends Action {
    private[TrackingCameraControls] var on: Boolean = false;
    override def perform(): Unit = {
      if (on) {
        on = false;
        unmarkAll();
        markLocal(tracked);
      } else {
        on = true;
        markAll();
      }
    }
  }

  private val keyboardActions = {
    val map = mutable.TreeMap.empty[Int, Action];
    map += (32 -> ShowAllPaths);
    map += (80 -> ToggleAllPaths);
    map
  }

  def track(obj: GraphicsObject): Unit = {
    val oldObj = tracked;
    trackingTransition = Some((lastTargetPosition.clone(), dom.window.performance.now()))
    tracked = obj;
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

  /** Interpolates from the displayed target towards the tracked object's live position.
    * A new tracking request starts from that displayed target, so retargeting has no jump.
    * @param nowMillis time from `window.performance.now()`
    */
  private def nextTrackingTarget(nowMillis: Double): Vector3 = trackingTransition match {
    case Some((startTarget, startedAt)) =>
      val elapsedMillis = nowMillis - startedAt
      val fractionComplete = math.min(1.0, math.max(0.0, elapsedMillis / trackingTransitionMillis))
      if (fractionComplete >= 1.0) trackingTransition = None
      val movement = tracked.position.clone()
      movement.sub(startTarget)
      movement.multiplyScalar(fractionComplete)
      val interpolatedTarget = startTarget.clone()
      interpolatedTarget.add(movement)
      interpolatedTarget
    case None => tracked.position
  }

  override def update() = {
    val nextTarget = nextTrackingTarget(dom.window.performance.now())
    val targetMovement = nextTarget.clone().sub(lastTargetPosition);
    camera.position.add(targetMovement);
    orbitControl.target.copy(nextTarget);
    orbitControl.update();
    lastTargetPosition.copy(nextTarget);
  }

  override def onMouseDown(event: MouseEvent): Unit = {
    this.element.focus(); // so that the keyboard commands only work in canvas
  }
  override def onMouseMove(event: MouseEvent): Unit = {
    this.onCursorMove(event.clientX, event.clientY);
  }

  def onKeyDown(event: KeyboardEvent): Unit = {
    this.keyboardActions.get(event.keyCode) match {
      case Some(action) => action.perform()
      case None         => () // ignore
    }
  }

  def onKeyUp(event: KeyboardEvent): Unit = {
    this.keyboardActions.get(event.keyCode) match {
      case Some(action: UndoableAction) => action.undo()
      case _                            => () // ignore
    }
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
    el.addEventListener("dblclick", (this.onDoubleClick _).asInstanceOf[Function[Event, _]], false);
    el.addEventListener("mousemove", (this.onMouseMove _).asInstanceOf[Function[Event, _]], false);
    el.addEventListener("mousedown", (this.onMouseDown _).asInstanceOf[Function[Event, _]], false);
    el.addEventListener("keydown", (this.onKeyDown _).asInstanceOf[Function[Event, _]], false);
    el.addEventListener("keyup", (this.onKeyUp _).asInstanceOf[Function[Event, _]], false);
  }
  this.attach(element);
}
