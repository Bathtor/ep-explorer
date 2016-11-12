package com.larskroll.ep.mapviewer

import org.denigma.threejs._
import org.denigma.threejs.extensions.controls.CameraControls
import org.denigma.threejs.extras.HtmlSprite
import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import scala.util.Random

import graphics._
import data.{ Planets, Stars }
import com.outr.scribe.Logging

import squants._

class SolarSystemScene(val container: HTMLElement, val width: Double, val height: Double) extends SceneContainer with TimeAnimatedScene with Tracking with Selecting with Logging {

    protected def nodeTagFromTitle(title: String) = p(title, `class` := s"ui large message").render

    val centre = new Vector3(0.0, 0.0, 0.0);

    val planets = Planets.list.map(addPlanet).toMap;

    var sprites = List.empty[HtmlSprite]

    override def distance: Double = space.AstronomicalUnits(1).toKilometers * Main.scaleDistance

    //scene.background = new Color(0x000000);
    //scene.background = background;

    val ambLight = new AmbientLight(0xFFFFFF, 0.1);
    scene.add(ambLight);
    val sun = Star.fromStarData(Stars.Sol);
    sun.moveTo(centre);
    sun.addToScene(this);

    val ctrls = new MapControls(camera, this.container, this, width, height, sun); //planets("Saturn").mesh);

    override val controls: CameraControls = ctrls;

    private def addPlanet(planetData: data.Planet): (String, Planet) = {
        val planet = Planet.fromPlanetData(planetData);
        (planet.name -> planet)
    }

    private def addLabel(pos: Vector3, title: String = "hello three.js and ScalaJS!"): HtmlSprite = {
        val helloHtml = nodeTagFromTitle(title)
        val html = new HtmlSprite(helloHtml)
        html.position.set(pos.x, pos.y, pos.z)
        html
    }

    planets.foreach(p => p._2.addToScene(this))
    //    planets.zipWithIndex.foreach {
    //        case (p, i) =>
    //            this.sprites = addLabel(p.mesh.position.clone().setY(p.mesh.position.y - 200), "Planet #" + i) :: this.sprites
    //
    //    }
    //    sprites.foreach(cssScene.add)

    // postprocessing
    //				val composer = new EffectComposer( renderer );
    //				composer.addPass( new THREE.RenderPass( scene, camera ) );
    //				val effect1 = new ShaderPass( DotScreenShader );
    //				effect1.uniforms('scale').value = 4;
    //				composer.addPass( effect1 );
    //				val effect2 = new THREE.ShaderPass( THREE.RGBShiftShader );
    //				effect2.uniforms('amount').value = 0.0015;
    //				effect2.renderToScreen = true;
    //				composer.addPass( effect2 );

    //scene.add(buildAxes(1e16));

    val texturePass = new facades.TexturePass(Textures("background"));

    override def passes = Seq(clearPass, texturePass, renderPass, copyPass);

    private def updatePositions() {
        sun.update(time)
        planets.foreach { p => p._2.update(time) }
    }

    private var running = false;
    private var time = Seconds(0.0);
    private var deltaTime = Seconds(0.0);

    override def start(): Unit = {
        running = true;
    }
    override def step(): Unit = {
        time += deltaTime;
        updatePositions();
        TimeControls.update(time)
    }
    override def stop(): Unit = {
        running = false;
    }
    override def setSpeed(t: Time): Unit = {
        deltaTime = t;
    }
    override def setOffset(t: Time): Unit = {
        time = t;
    }

    override def animate() {
        if (running) {
            step();
        }
    }

    override def select(obj: graphics.GraphicsObject): Unit = {
        ctrls.select(obj);
    }

    override def track(obj: graphics.GraphicsObject): Unit = {
        ctrls.track(obj);
    }

    def buildAxes(length: Double): Object3D = {
        val axes = new Object3D();

        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(length, 0, 0), 0xFF0000, false, "+X")); // +X
        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(-length, 0, 0), 0xFF0000, true, "-X")); // -X
        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(0, length, 0), 0x00FF00, false, "+Y")); // +Y
        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(0, -length, 0), 0x00FF00, true, "-Y")); // -Y
        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(0, 0, length), 0x0000FF, false, "+Z")); // +Z
        axes.add(buildAxis(new Vector3(0, 0, 0), new Vector3(0, 0, -length), 0x0000FF, true, "-Z")); // -Z

        return axes;

    }

    def buildAxis(src: Vector3, dst: Vector3, colorHex: Double, dashed: Boolean, name: String): Line = {
        val geom = new Geometry();

        val mat = if (dashed) {
            new LineDashedMaterial(js.Dynamic.literal(linewidth = 3.0, color = colorHex, dashSize = 3.0, gapSize = 3.0).asInstanceOf[LineDashedMaterialParameters]);
        } else {
            new LineBasicMaterial(js.Dynamic.literal(linewidth = 3.0, color = colorHex).asInstanceOf[LineBasicMaterialParameters]);
        }

        geom.vertices.push(src.clone());
        geom.vertices.push(dst.clone());
        geom.computeLineDistances(); // This one is SUPER important, otherwise dashed lines will appear as simple plain lines

        val l = new Line(geom, mat);
        l.name = name;
        l
    }
}