package com.lkroll.ep.mapviewer

import org.denigma.threejs._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import scala.scalajs.js

import js.JSConverters._

trait IntersectionControls {
  def camera: Camera
  def scene: Object3D
  def element: HTMLElement
  def sceneObjects: Array[Object3D]
  def overlayObjects: Array[Object3D]

  val rayLength = 1e16;
  val rayOffset = new Vector3(0.0, 0.0, 0.0);

  //    lazy val (raycaster, ray) = {
  //        val rc = new Raycaster();
  //        //rc.setFromCamera(mouse, camera);
  //        val r = buildRay(rc.ray.origin, rc.ray.direction);
  //        r.matrixAutoUpdate = true;
  //        //scene.add(r);
  //        (rc, r)
  //    }

  lazy val raycaster = new Raycaster();

  lazy val screenT = {
    val rect = element.getBoundingClientRect();
    //        val width = if (rect.width == 0.0) {
    //            dom.window.innerWidth
    //        } else {
    //            rect.width
    //        }
    //        val height = if (rect.height == 0.0) {
    //            dom.window.innerHeight
    //        } else {
    //            rect.height
    //        }
    val (width, height) = (dom.window.innerWidth, dom.window.innerHeight);
    //println(s"Bounding rectangle: left=${rect.left}, top=${rect.top}, width=${width}, hight=${height}")
    new graphics.ScreenTransform(width, height, rect.left, rect.top)
  }

  var intersections = List.empty[Intersection]
  var underMouse = Map.empty[Object3D, List[Intersection]]
  var last = Map.empty[Object3D, List[Intersection]]
  var exit = Map.empty[Object3D, List[Intersection]]
  var enter = Map.empty[Object3D, List[Intersection]]

  def findIntersections(mouse: Vector2): List[Intersection] = {
    //println(s"Mouse position: ${mouse.toArray().mkString(",")}");
    raycaster.setFromCamera(mouse, camera);
    //updateRay(raycaster.ray.origin, raycaster.ray.direction)
    //println(s"Raycaster: ${raycaster.ray.origin.toArray().mkString(",")} -> ${raycaster.ray.direction.toArray().mkString(",")}, near=${raycaster.near},far=${raycaster.far}");
    val sceneIntersections = raycaster.intersectObjects(sceneObjects.toJSArray); //.sortWith((a, b) => a.point.distanceTo(raycaster.ray.origin) < b.point.distanceTo(raycaster.ray.origin)).toList
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
    //        val ss = screenT.toScreenSpace(ncs);
    //        println(s"coords=${coords.pretty}, ncs=${ncs.pretty}, ss=${ss.pretty}, screenT=${screenT}");
    intersections = findIntersections(ncs);
    //println(s"Intersections: ${intersections.map { x => x.`object`.name }.mkString(",")}")
    underMouse = intersections.groupBy(_.`object`)
    val l = last // if I do not do this assigment and use last instead of l I get into trouble
    this.exit = l.filterKeys(!underMouse.contains(_))
    this.enter = underMouse.filterKeys(!l.contains(_))
    // if(exit.exists{case (key,value)=>enter.contains(key)}) dom.console.error("same enterexit")
    //val s = enter.size
    last = underMouse
    //if (s != enter.size) dom.console.error("ScalaJS error with immutable collections")
  }

}
