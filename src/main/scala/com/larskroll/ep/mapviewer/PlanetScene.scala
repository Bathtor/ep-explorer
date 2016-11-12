package com.larskroll.ep.mapviewer

import org.denigma.threejs._
import org.denigma.threejs.extensions.Container3D
import org.denigma.threejs.extensions.controls.CameraControls
import org.denigma.threejs.extras.HtmlSprite
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import scala.util.Random

import graphics._
import data.{ Planets, Planet => PlanetData }

class PlanetScene(val planetData: PlanetData, val container: HTMLElement, val width: Double, val height: Double) extends SceneContainer {

    protected def nodeTagFromTitle(title: String) = p(title, `class` := s"ui large message").render

    val planet = addPlanet(planetData);
    val centre = new Vector3(0.0, 0.0, 0.0);

    //var meshes = addPlanet(new Vector3(0, -400, 0), "green") :: Nil

    var sprites = List.empty[HtmlSprite]

    override val controls: CameraControls = new MapControls(camera, this.container, this, width, height, planet)

    val light = new DirectionalLight(0xffffff, 2)
    light.position.set(1, 1, 1).normalize()
    scene.add(light)

    def addPlanet(p: PlanetData): Planet = {
        val planet = Planet.fromPlanetData(p);
        planet.moveTo(centre);
        planet
    }

    def addLabel(pos: Vector3, title: String = "hello three.js and ScalaJS!"): HtmlSprite = {
        val helloHtml = nodeTagFromTitle(title)
        val html = new HtmlSprite(helloHtml)
        html.position.set(pos.x, pos.y, pos.z)
        html
    }

    planet.addToScene(this);
}