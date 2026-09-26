package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject
import com.lkroll.ep.mapviewer.{ExtObject3D, Main, SceneContainer, Textures}
import com.lkroll.ep.mapviewer.three._

import scala.scalajs.js
import scribe.Logging
import squants.Time

class TacticalOverlay(obj: AstronomicalObject) extends GraphicsObject with Overlayed {
  private val geometry = new BufferGeometry().setFromPoints(js.Array(new Vector3(0, 0, 0)));
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

  override def moveTo(pos: Vector3): Unit = {
    mesh.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer): Unit = {
    throw new RuntimeException("Use objects addToScene instead of overlay's");
  }

  override def update(t: Time): Unit = {
    throw new RuntimeException("Use objects update instead of overlay's");
  }

  override def children = List.empty[GraphicsObject];

  override def name = mesh.name;

  override def position = mesh.position;

  override def id = mesh.id;

  def hover(): Unit = {
    mesh.material.asInstanceOf[PointsMaterial].color.set(TacticalOverlay.hoverColor)
  }

  def select(): Unit = {
    material.color.set(TacticalOverlay.selectedColor)
  }

  def clear(): Unit = {
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
  def intersectObjects(mouseNdc: Vector2,
                       camera: Camera,
                       screenT: ScreenTransform,
                       overlayObjects: Array[Object3D]): Array[Intersection] = {
    val mousePixels = screenT.toScreenSpace(mouseNdc);
    overlayObjects.flatMap(o => intersectObject(mousePixels, camera, screenT, o))
  }

  def intersectObject(mousePixels: Vector2, camera: Camera, screenT: ScreenTransform, obj: Object3D): Option[Intersection] = {
    if (obj.isInstanceOf[Points]) {
      val pointsObject = obj.asInstanceOf[Points];
      val material = pointsObject.material.asInstanceOf[PointsMaterial];
      val worldPosition = pointsObject.position;
      val projectedPositionNdc = worldPosition.clone().project(camera);
      val screenPositionPixels = screenT.toScreenSpace(projectedPositionNdc);
      val pixelDistance = screenPositionPixels.distanceTo(mousePixels);
      if (pixelDistance > (material.size / 2)) {
        None
      } else {
        val worldDistance = camera.position.distanceTo(worldPosition);
        val ints = js.Dynamic.literal(distance = worldDistance,
                                      `object` = pointsObject);
        Some(ints.asInstanceOf[Intersection])
      }
    } else {
      logger.error(s"Object was not of type Points: ${obj.name}");
      None
    }
  }
}
