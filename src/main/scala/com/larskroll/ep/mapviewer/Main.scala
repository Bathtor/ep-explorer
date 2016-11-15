package com.larskroll.ep.mapviewer

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import scala.scalajs.js
//import org.scalajs.dom.html
import scalatags.JsDom.all._
//import scalatags.JsDom
import com.outr.scribe.Logging

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D
import org.denigma.threejs.extensions.controls.{ CameraControls, JumpCameraControls }
import org.denigma.threejs.extras.HtmlSprite
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.window

import org.scalajs.dom.raw.{ HTMLTextAreaElement, HTMLElement }

import scala.util.Random

import squants.time._

object Main extends JSApp with Logging {

    val scale = 1e-6;
    val scaleDistance = 1e-6;
    val pixelRatio = dom.window.devicePixelRatio;
    //val timeFactor = Minutes(1);
    var scene: Option[SceneContainer with TimeAnimatedScene] = None;

    def main(): Unit = {
        //        val canvas: html.Canvas = document.body.children.namedItem("canvas").asInstanceOf[html.Canvas];
        //        logger.debug(s"Starting up with canvas ${canvas.id}");
        //        val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D];
        val nojs = document.body.children.namedItem("nojs");
        document.body.removeChild(nojs);
        val ctx = div(id := "context").render
        document.body.appendChild(ctx);
        UI.render();
        logger.info(s"Starting up with context ${ctx.id}");
        val height = window.innerHeight;
        val width = window.innerWidth;
        val textures = Map("background" -> "starfield4k.png", "overlay" -> "hexagon.png");
        var countLoaded = 0;

        //        val scene = new PlanetScene(ctx, width, height);
        //        scene.render()
        def addTexture(key: String)(texture: Texture): Unit = {
            Textures.put(key, texture);
            countLoaded += 1;
            if (countLoaded == textures.size) {
                val sscene = new SolarSystemScene(ctx, width, height);
                sscene.render();
                scene = Some(sscene)
                sscene.step();
            }
        }
        val backgroundLoader = new TextureLoader();
        textures.foreach { case (key, url) => backgroundLoader.load(url, addTexture(key) _, onPrint _, onError _) }

    }

    private def onPrint(xhr: dom.XMLHttpRequest): Unit = println("still loading...");
    private def onError(xhr: dom.XMLHttpRequest): Unit = println(s"Error: ${xhr}");

}