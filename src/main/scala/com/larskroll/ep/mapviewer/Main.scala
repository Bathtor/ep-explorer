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
    var debug: Boolean = false;
    var url: String = {
        val urlparts = document.URL.split("\\?");
        urlparts(0)
    }

    def main(): Unit = {
        //        val canvas: html.Canvas = document.body.children.namedItem("canvas").asInstanceOf[html.Canvas];
        //        logger.debug(s"Starting up with canvas ${canvas.id}");
        //        val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D];
        val nojs = document.body.children.namedItem("nojs");
        document.body.removeChild(nojs);
        val ctx = div(id := "context").render
        document.body.appendChild(ctx);

        val params = QueryParams.fromURI(document.URL);

        params.get("debug") match {
            case Some("false") | Some("no") | None => debug = false;
            case _                                 => debug = true;
        }

        UI.render();

        val height = window.innerHeight;
        val width = window.innerWidth;
        val textures = Map("background" -> "starfield4k.png", "overlay" -> "hexagon.png", "planet" -> "planet.jpg");
        var countLoaded = 0;

        //        val scene = new PlanetScene(ctx, width, height);
        //        scene.render()
        def addTexture(key: String)(texture: Texture): Unit = {
            Textures.put(key, texture);
            countLoaded += 1;
            if (countLoaded == textures.size) {
                selectScene(params, ctx, width, height) match {
                    case Left(sscene) => {
                        sscene.render();
                        scene = Some(sscene)
                        sscene.step();
                    }
                    case Right(msg) => {
                        document.body.removeChild(ctx);
                        UI.renderError(msg);
                    }
                }
            }
        }
        val backgroundLoader = new TextureLoader();
        textures.foreach { case (key, url) => backgroundLoader.load(url, addTexture(key) _, onPrint _, onError _) }

    }

    def getUrlFor(params: QueryParams): String = {
        params.toURI(this.url)
    }
    
    private def selectScene(params: QueryParams, ctx: HTMLElement, width: Double, height: Double): Either[SceneContainer with TimeAnimatedScene, String] = {
        params.get("view") match {
            case Some("system") => {
                Left(new SolarSystemScene(ctx, width, height))
            }
            case Some("single") => {
                params.get("target") match {
                    case Some(name) => {
                        data.findObjectForName(name) match {
                            case Some(obj) => Left(new SingleScene(obj, ctx, width, height))
                            case None      => Right(s"No astronomical object of name '${name}' found!")
                        }
                    }
                    case None => Right(s"Missing 'target' parameter.")
                }
            }
            case Some(x) => {
                Right(s"Invalid view parameter ${x}. Expected 'single' or 'system'.")
            }
            case None => {
                logger.info("No view parameter giving, defaulting to solar system view.");
                Left(new SolarSystemScene(ctx, width, height))
            }
        }
    }

    private def onPrint(xhr: dom.XMLHttpRequest): Unit = println("still loading...");
    private def onError(xhr: dom.XMLHttpRequest): Unit = println(s"Error: ${xhr}");

}