package com.lkroll.ep.mapviewer.graphics

import org.denigma.threejs._

import scalatags.JsDom.all._

import collection.mutable
import scala.scalajs.js
import js.JSConverters._
import org.scalajs.dom.html
import org.scalajs.dom.document

import com.lkroll.ep.mapviewer.datamodel.{ AstronomicalObject, ExtraUnits, ConstantOriginOrbit }
import com.lkroll.ep.mapviewer.facades.{ CSS3DObject, CSS3DSprite }
import com.lkroll.ep.mapviewer.{ Main, ExtObject3D, SceneContainer };

import squants.time._

class TextSprite(
  val text:   String,
  val colour: Color  = new Color(0x808080) //new Color(0xFFFFFF)
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

    //ctx.f.font = fontFace;
    ctx.font = fontFace;
    textWidth = ctx.measureText(text).width.asInstanceOf[Double] / 4.0;
    textHeight = TextSprite.measureFontHeight(fontFace) / 4.0;

    //val wp = TextSprite.ceilPowerOfTwo(textWidth);
    //val hp = TextSprite.ceilPowerOfTwo(textHeight);
    //hwp = Math.max(wp, hp);

    canvas.width = canvasSize;
    canvas.height = canvasSize;

    ctx.fillStyle = fillStyle
    ctx.textAlign = align;
    ctx.textBaseline = "top";
    //this.ctx.shadowColor = ctxOptions.shadowColor;
    //this.ctx.shadowBlur = ctxOptions.shadowBlur;
    //this.ctx.shadowOffsetX = ctxOptions.shadowOffsetX;
    //this.ctx.shadowOffsetY = ctxOptions.shadowOffsetY;

    val offsetH = Math.max(0, (canvasSize / 2) - (textWidth / 2.0).toInt);
    val offsetV = Math.max(0, (canvasSize / 2) - (textHeight / 2.0).toInt);

    // println(s"Drawing size=$size with text=($textWidth, $textHeight) and canvas=(${canvas.width}, ${canvas.height}) and offset=($offsetH, $offsetV)");

    ctx.fillText(text, offsetH, offsetV);
    //ctx.strokeStyle = "green";
    //ctx.strokeRect(0, 0, canvasSize, canvasSize);
  }
  drawText();

  val texture = {
    val t = new Texture(canvas);
    t.magFilter = THREE.NearestFilter;
    t.minFilter = THREE.LinearMipMapLinearFilter;
    t
  }

  //val material = new SpriteMaterial(js.Dynamic.literal(map = texture, blending = THREE.AdditiveBlending, depthTest = false, transparent = true).asInstanceOf[SpriteMaterialParameters]);
  private val material = new PointsMaterial(js.Dynamic.literal(size = size, map = texture, blending = THREE.NormalBlending, depthTest = false, transparent = true, sizeAttenuation = false, color = colour).asInstanceOf[PointsMaterialParameters]);
  private val geometry = new Geometry();
  geometry.vertices.push(new Vector3(0, 0, 0));
  val sprite = new Points(geometry, material);

  texture.needsUpdate = true;

  //val sprite = new Sprite(material);
  //sprite.scale.set(canvas.width, this.canvas.height, 1);

  override def moveTo(pos: Vector3): Unit = {
    sprite.moveTo(pos);
  }

  override def addToScene(scene: SceneContainer): Unit = {
    throw new RuntimeException("Use objects addToScene instead of overlay's");
    //scene.addObject(this, sprite);
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
    fontHeightCache.getOrElseUpdate(fontStyle, {
      val body = document.getElementsByTagName("body")(0);
      val dummy = document.createElement("div");

      val dummyText = document.createTextNode("0.9AU");
      dummy.appendChild(dummyText);
      dummy.setAttribute("style", s"font:${fontStyle};position:absolute;top:0;left:0;margin: 0px; padding: 0px; line-height: 1;");
      body.appendChild(dummy);
      val result = dummy.clientHeight;
      body.removeChild(dummy);
      result.toDouble
    })
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
