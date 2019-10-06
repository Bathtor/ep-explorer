package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._

import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject
import com.lkroll.ep.mapviewer.{ExtObject3D, ExtVector2, Main, SceneContainer, Textures}

import scala.scalajs.js
import js.JSConverters._

import squants.Time

import scribe.Logging

class TacticalOverlay(obj: AstronomicalObject) extends GraphicsObject with Overlayed {
  private val geometry = new Geometry();
  geometry.vertices.push(new Vector3(0, 0, 0));
  private val texture = Textures("overlay");
  private val material = new PointsMaterial(
    js.Dynamic
      .literal(
        size = TacticalOverlay.overlaySize,
        map = texture,
        blending = THREE.AdditiveBlending,
        depthTest = false,
        transparent = true,
        sizeAttenuation = false,
        color = TacticalOverlay.defaultColor
      )
      .asInstanceOf[PointsMaterialParameters]
  );
  val mesh = new Points(geometry, material);
  mesh.name = obj.name + " Overlay";
  //mesh

  override def moveTo(pos: Vector3) {
    mesh.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer) {
    //scene.addOverlayObject(this, mesh);
    throw new RuntimeException("Use objects addToScene instead of overlay's");
  }

  override def update(t: Time) {
    throw new RuntimeException("Use objects update instead of overlay's");
  }

  override def children = List.empty[GraphicsObject];

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  def hover() {
    mesh.material.asInstanceOf[PointsMaterial].color.set(TacticalOverlay.hoverColor)
  }

  def select() {
    material.color.set(TacticalOverlay.selectedColor)
  }

  def clear() {
    material.color.set(TacticalOverlay.defaultColor)
  }

  def overlay = this;

  override def boundingRadius: Double = 0.0; // not applicable

}

object TacticalOverlay extends Logging {

  val selectedColor = new Color(0xD17B5E);
  val hoverColor = new Color(0xD1B35E);
  val defaultColor = new Color(0x406A86);
  val overlaySize = 64.0 / Main.pixelRatio; // make it smaller on higher resolution displays

  def from(obj: AstronomicalObject): TacticalOverlay = {
    new TacticalOverlay(obj)
  }
  def intersectObjects(mouse: Vector2,
                       camera: Camera,
                       screenT: ScreenTransform,
                       overlayObjects: Array[Object3D]): Array[Intersection] = {
    val screenMouse = screenT.toScreenSpace(mouse);
    overlayObjects.flatMap(o => intersectObject(screenMouse, camera, screenT, o))
  }

  def intersectObject(mouse: Vector2, camera: Camera, screenT: ScreenTransform, obj: Object3D): Option[Intersection] = {
    if (obj.isInstanceOf[Points]) {
      val point = obj.asInstanceOf[Points];
      val material = point.material.asInstanceOf[PointsMaterial];
      val geometry = point.geometry;
      //            val inverseProjection = new Matrix4();
      //            inverseProjection.getInverse(camera.projectionMatrix);
      val pos = new Vector3();
      pos.copy(point.position);
      pos.project(camera);
      val screenPos = screenT.toScreenSpace(pos);
      //logger.info(s"mouse at ${mouse.pretty} and pos at ${screenPos.pretty}");
      val dist = screenPos.distanceTo(mouse);
      if (dist > (material.size / 2)) {
        None
      } else {
        val intersectPointScreen = new Vector2();
        intersectPointScreen.subVectors(mouse, pos);
        val intersectPointNCS = screenT.toNormalizedCameraSpace(intersectPointScreen);
        val intersectPoint = new Vector3();
        intersectPoint.set(intersectPointNCS.x, intersectPointNCS.y, 0.5);
        intersectPoint.project(camera);
        intersectPoint.setZ(pos.z);
        val rayDistance = camera.position.distanceTo(pos);
        val ints = js.Dynamic.literal(distance = rayDistance,
                                      distanceToRay = dist,
                                      point = intersectPoint,
                                      index = 0,
                                      face = null,
                                      `object` = point);
        Some(ints.asInstanceOf[Intersection])
      }
    } else {
      logger.error(s"Object was not of type Points: ${obj.name}");
      //            val is = raycaster.intersectObject(obj);
      //            is.headOption
      None
    }
  }
}
