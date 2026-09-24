package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.three._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.util.Random

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.window
import org.scalajs.dom.{HTMLElement, HTMLTextAreaElement}
import scalatags.JsDom.all._
import scribe.Logging
import squants.time._

object Main extends Logging {

  val renderUp: Boolean = false;
  val scale = 1e-6;
  val scaleDistance = 1e-6;
  val pixelRatio = dom.window.devicePixelRatio;
  val starttime = datamodel.AFTT(3653.0).to(datamodel.J2000TT); //data.JulianDateTT(2451623.81597).to(data.J2000TT);//Seconds(0.0);
  //val timeFactor = Minutes(1);
  var scene: Option[SceneContainer with TimeAnimatedScene] = None;
  var url: String = {
    val urlparts = document.URL.split("\\?");
    urlparts(0)
  }
  var opts: MapOptions = MapOptions.default;

  def main(args: Array[String]): Unit = {
    //        val canvas: html.Canvas = document.body.children.namedItem("canvas").asInstanceOf[html.Canvas];
    //        logger.debug(s"Starting up with canvas ${canvas.id}");
    //        val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D];
    val nojs = document.body.children.namedItem("nojs");
    document.body.removeChild(nojs);
    val ctx = div(id := "context", tabindex := 0).render
    document.body.appendChild(ctx);

    UI.render();

    val params = QueryParams.fromURI(document.URL);
    opts = MapOptions(params);
    val issues = opts.verify();

    if (!issues.isEmpty) {
      document.body.removeChild(ctx);
      UI.renderError(issues);
      issues.foreach { f =>
        logger.error(f.exception)
      }
      return ();
    }

    UI.renderLoading();

    val height = window.innerHeight;
    val width = window.innerWidth;
    val textures = Map("background" -> "starfield4k.png", "overlay" -> "hexagon.png", "planet" -> "planet.jpg");
    var countLoaded = 0;

    UI.updateLoaded(countLoaded, textures.size);

    def addTexture(key: String)(texture: Texture): Unit = {
      Textures.put(key, texture);
      countLoaded += 1;
      UI.updateLoaded(countLoaded, textures.size);
      if (countLoaded == textures.size) {
        selectScene(ctx, width, height) match {
          case Left(sscene) => {
            scene = Some(sscene);
            sscene.render();
            sscene.step();
          }
          case Right(msg) => {
            document.body.removeChild(ctx);
            UI.renderError(msg);
          }
        }
        UI.hideLoading();
      }
    }
    val backgroundLoader = new TextureLoader();
    textures.foreach { case (key, url) => backgroundLoader.load(url, addTexture(key) _, onPrint _, onError(url) _) }

  }

  def getUrlFor(params: QueryParams): String = {
    params.toURI(this.url)
  }

  private def selectScene(ctx: HTMLElement,
                          width: Double,
                          height: Double): Either[SceneContainer with TimeAnimatedScene, String] = {
    opts.view() match {
      case View.Single => {
        opts.target.get match {
          case Some(ao) => Left(new SingleScene(ao, ctx, width, height))
          case None     => Right(s"Missing 'target' parameter for Single view.")
        }
      }
      case View.System => {
        Left(new SolarSystemScene(ctx, width, height))
      }
    }
  }

  private def onPrint(event: dom.Event): Unit = println("still loading...");
  private def onError(url: String)(event: dom.Event): Unit = logger.error(s"Failed to load texture: $url");

}
