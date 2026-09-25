package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.three._

import scala.scalajs.js.JSConverters._
import org.scalajs.dom
import org.scalajs.dom.HTMLElement

trait IntersectionControls {
  def camera: Camera
  def scene: Object3D
  def element: HTMLElement
  def sceneObjects: Array[Object3D]
  def overlayObjects: Array[Object3D]

  val rayLength = 1e16;
  val rayOffset = new Vector3(0.0, 0.0, 0.0);

  lazy val raycaster = new Raycaster();

  lazy val screenT = {
    val rect = element.getBoundingClientRect();
    val (width, height) = (dom.window.innerWidth, dom.window.innerHeight);
    new graphics.ScreenTransform(width, height, rect.left, rect.top)
  }

  var intersections = List.empty[Intersection]
  var underMouse = Map.empty[Object3D, List[Intersection]]
  var last = Map.empty[Object3D, List[Intersection]]
  var exit = Map.empty[Object3D, List[Intersection]]
  var enter = Map.empty[Object3D, List[Intersection]]

  def findIntersections(mouse: Vector2): List[Intersection] = {
    raycaster.setFromCamera(mouse, camera);
    val sceneIntersections = raycaster.intersectObjects(sceneObjects.toJSArray);
    val overlayIntersections = graphics.TacticalOverlay.intersectObjects(mouse, camera, screenT, overlayObjects);
    val intersectionsB = List.newBuilder[Intersection];
    intersectionsB ++= sceneIntersections;
    intersectionsB ++= overlayIntersections;
    intersectionsB.result()
  }

  val coords = new Vector2();

  def onCursorMove(cordX: Double, cordY: Double): Unit = {
    coords.set(cordX, cordY);
    val ncs = screenT.toNormalizedCameraSpace(coords);
    intersections = findIntersections(ncs);
    underMouse = intersections.groupBy(_.`object`)
    val l = last // if I do not do this assigment and use last instead of l I get into trouble
    this.exit = l.filter { case (key, _) => !underMouse.contains(key) }
    this.enter = underMouse.filter { case (key, _) => !l.contains(key) }
    last = underMouse
  }

}
