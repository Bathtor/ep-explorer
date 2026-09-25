package com.lkroll.ep.mapviewer.graphics

import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject
import com.lkroll.ep.mapviewer.{ExtObject3D, Main, SceneContainer};
import com.lkroll.ep.mapviewer.three._

import scala.collection.mutable

import org.scalajs.dom.document
import org.scalajs.dom.html
import scala.scalajs.js
import scalatags.JsDom.all._
import squants.time._

class TextSprite(
    val text: String,
    val colour: Color = new Color(0x808080)
) extends GraphicsObject {

  val size: Double = 64.0 / Main.pixelRatio;
  val canvasSize = (size * Main.pixelRatio).toInt;
  val fontFace = s"""bold 64px "Monaco", monospace""";
  val fillStyle = "#FFFFFF";
  val align = textAlign.left.v;

  val canvas: html.Canvas = document.createElement("canvas").asInstanceOf[html.Canvas];
  val ctx = canvas.getContext("2d");

  private var textWidth: Double = 0.0;
  private var textHeight: Double = 0.0;

  def drawText(): Unit = {
    ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

    ctx.font = fontFace;
    textWidth = ctx.measureText(text).width.asInstanceOf[Double] / 4.0;
    textHeight = TextSprite.measureFontHeight(fontFace) / 4.0;

    canvas.width = canvasSize;
    canvas.height = canvasSize;

    ctx.fillStyle = fillStyle
    ctx.textAlign = align;
    ctx.textBaseline = "top";
    val offsetH = Math.max(0, (canvasSize / 2) - (textWidth / 2.0).toInt);
    val offsetV = Math.max(0, (canvasSize / 2) - (textHeight / 2.0).toInt);

    ctx.fillText(text, offsetH, offsetV);
  }
  drawText();

  val texture = {
    val t = new CanvasTexture(canvas);
    t.magFilter = THREE.NearestFilter;
    t.minFilter = THREE.LinearMipmapLinearFilter;
    t.colorSpace = THREE.SRGBColorSpace;
    t
  }

  private val material = new PointsMaterial(
    js.Dynamic
      .literal(size = size,
               map = texture,
               blending = THREE.NormalBlending,
               depthTest = false,
               transparent = true,
               sizeAttenuation = false,
               color = colour)
      .asInstanceOf[PointsMaterialParameters]
  );
  private val geometry = new BufferGeometry().setFromPoints(js.Array(new Vector3(0, 0, 0)));
  val sprite = new Points(geometry, material);

  override def moveTo(pos: Vector3): Unit = {
    sprite.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer): Unit = {
    throw new RuntimeException("Use objects addToScene instead of overlay's");
  }

  override def update(time: Time): Unit = { throw new RuntimeException("Use objects update instead of overlay's"); }

  override def children: List[GraphicsObject] = List.empty;

  override def name: String = s"Label '${text}'";

  override def position: Vector3 = sprite.position;

  override def id: Double = sprite.id;

  override def boundingRadius: Double = Math.max(textWidth, textHeight);

  override def represents(ao: AstronomicalObject): Boolean = false;
}

object TextSprite {
  private var fontHeightCache = mutable.Map.empty[String, Double];

  def measureFontHeight(fontStyle: String): Double = {
    fontHeightCache.getOrElseUpdate(
      fontStyle, {
        val body = document.getElementsByTagName("body")(0);
        val dummy = document.createElement("div");

        val dummyText = document.createTextNode("0.9AU");
        dummy.appendChild(dummyText);
        dummy.setAttribute(
          "style",
          s"font:${fontStyle};position:absolute;top:0;left:0;margin: 0px; padding: 0px; line-height: 1;"
        );
        body.appendChild(dummy);
        val result = dummy.clientHeight;
        body.removeChild(dummy);
        result.toDouble
      }
    )
  }

  def ceilPowerOfTwo(d: Double): Int = {
    val base = 2.0;
    var exp = 1.0;
    var pow = Math.pow(base, exp);
    while (pow < d) {
      exp = exp + 1.0;
      pow = Math.pow(base, exp);
    }
    pow.toInt
  }
}
