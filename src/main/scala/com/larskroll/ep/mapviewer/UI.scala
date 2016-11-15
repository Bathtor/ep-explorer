package com.larskroll.ep.mapviewer

import org.scalajs.dom
import org.scalajs.dom.MouseEvent
import dom.document
import scalatags.JsDom.all._
import com.outr.scribe.Logging
import scala.scalajs.js.Date

import data._

import squants.time._

object UI extends Logging {
    def render() {
        document.body.appendChild(top.render);
        document.body.appendChild(info.render);
        document.body.appendChild(TimeControls.block);
    }

    def label(text: String) = span(text, cls := "color-primary-0", style := "font-weight: bold;")
    def label(text: String, extracls: String) = span(text, cls := "color-primary-0 " + extracls, style := "font-weight: bold;")

    
    
    val trackingContent = span("Nothing").render;

    private val data = datalist(id := "data").render;
    private val search = input(list := "data").render;
    private val track = button(`type` := "button", name := "track", "track").render;
    track.onclick = (e: MouseEvent) => {
        if (!search.value.isEmpty()) {
            searchObject(search.value) match {
                case Some(obj) => {
                    Main.scene match {
                        case Some(scene: Tracking) =>
                            scene.track(obj); search.value = ""
                        case _ => logger.info("Scene does not support tracking")
                    }
                }
                case None => logger.info("No object found!")
            }
        }
    };
    private val infoButton = button(`type` := "button", name := "infoButton", "info").render;
    infoButton.onclick = (e: MouseEvent) => {
        if (!search.value.isEmpty()) {
            searchObject(search.value) match {
                case Some(obj) => {
                    Main.scene match {
                        case Some(scene: Selecting) =>
                            scene.select(obj); search.value = ""
                        case _ => logger.info("Scene does not support info")
                    }
                }
                case None => logger.info("No object found!")
            }
        }
    };

    val top = div(id := "topmenu",
        img(src := "eclipse_phase.png", id := "logo"),
        span("Solar System Explorer", id := "title"),
        label("Currently Tracking: ", "leftSpace"), trackingContent,
        div(id := "search", label("Search"), data, search, track, infoButton));

    //    def updateData(items: Seq[String]) {
    //        data.innerHTML = "";
    //        items.foreach(item => data.appendChild(option(value := item).render));
    //    }

    def addData(item: String) {
        data.appendChild(option(value := item).render)
    }

    private def searchObject(in: String): Option[graphics.GraphicsObject] = {
        Main.scene match {
            case Some(scene) => scene.searchIndex.get(in)
            case None        => None
        }
    }

    val infoContent = div(id := "info-block", "test").render;

    val info = div(id := "info",
        h3("Info", cls := "color-primary-0"),
        infoContent);

    def replaceTracking(objO: Option[AstronomicalObject]) {
        trackingContent.innerHTML = "";
        objO match {
            case Some(o) => trackingContent.appendChild(o.name.render)
            case _       => trackingContent.appendChild("Nothing".render)
        }
    }

    def replaceInfo(objO: Option[AstronomicalObject]) {
        infoContent.innerHTML = "";
        objO match {
            case Some(obj) => {
                val info = extractInfo(obj);
                val infoHTML = info.flatMap {
                    case (key, value) =>
                        Seq(dt(key), dd(value))
                };
                UI.infoContent.appendChild(dl(infoHTML).render);
            }
            case None => UI.infoContent.appendChild(p("No information available...").render);
        }
    }

    private def extractInfo(obj: AstronomicalObject): List[Tuple2[String, String]] = {
        val infoB = List.newBuilder[Tuple2[String, String]];
        infoB += ("Name" -> obj.name);
        infoB += ("Type" -> obj.`type`);
        obj match {
            case star: Star => {
                infoB += ("Mass" -> format(star.mass.toKilograms, "kg"));
                infoB += ("Radius" -> format(star.radius.toKilometers, "km"));
            }
            case planet: Planet => {
                infoB += ("Mass" -> format(planet.mass.toKilograms, "kg"));
                infoB += ("Radius" -> format(planet.radius.toKilometers, "km"));
            }
            case moon: Moon => {
                infoB += ("Mass" -> format(moon.mass.toKilograms, "kg"));
                infoB += ("Radius" -> format(moon.radius.toKilometers, "km"));
            }
            case habitat: Habitat => {
                infoB += ("Station Type" -> habitat.stationType.name);
                infoB += ("Allegiance" -> habitat.allegiance.description);
                infoB += ("Primary Languages" -> habitat.langs.mkString(", "));
                infoB += ("Major Industries" -> habitat.industries.mkString(", "));
            }
            case _ => // ignore
        }
        obj match {
            case orbiter: Orbiting => {
                infoB += ("Orbital Period" -> format(orbiter.orbit.orbitalPeriod.toDays, "days(TT)"));
            }
        }
        infoB ++= obj.extraInfo;
        obj.description.foreach { d =>
            infoB += ("Description" -> d);
        }
        infoB.result()
    }

    private def format(value: Double, unit: String): String = {
        if (value >= 1e4) {
            f"${value}%.5e$unit"
        } else {
            f"${value}%.5f$unit"
        }
    }

}