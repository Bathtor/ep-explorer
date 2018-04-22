package com.lkroll.ep.mapviewer

import org.scalajs.dom
import org.scalajs.dom.MouseEvent
import dom.document
import scalatags.JsDom.all._
import scribe.Logging
import scala.scalajs.js.Date

import datamodel._

import squants.time._

object TimeControls extends Logging {

  private var editing = true;

  private val epoch = select(
    size := "1",
    option(value := "AF", "AF", selected),
    option(value := "BF", "BF"),
    option(value := "J2000", "J2000"),
    option(value := "JD", "JD"),
    option(value := "Unix", "Unix")).render;
  private val yearField = input(`type` := "number", min := "-9999", max := "9999").render;
  private val dayField = input(`type` := "number", min := "1", max := "365").render;
  private val hourField = input(`type` := "number", min := "0", max := "23").render;
  private val minuteField = input(`type` := "number", min := "0", max := "59").render;
  private val secondField = input(`type` := "number", min := "0", max := "59").render;
  private val timeFields = Seq(hourField, minuteField, secondField, dayField, yearField);
  // second row
  private val speed = select(
    size := "1",
    option(value := "s", "1s"),
    option(value := "m", "1min", selected),
    option(value := "h", "1h"),
    option(value := "d", "1d")).render;
  private val play = button(`type` := "button", name := "play", raw("&#x25B6;")).render;
  play.onclick = (e: MouseEvent) => {
    if (editing) {
      editMode(false);
      Main.scene match {
        case Some(scene) => {
          updateScene(scene);
          scene.start();
        }
        case _ => logger.info("No scene established, time controls are ignored!");
      }
    }
  };
  private val stop = button(`type` := "button", name := "stop", raw("&#x25A0;")).render;
  stop.onclick = (e: MouseEvent) => {
    if (!editing) {
      editMode(true);
      Main.scene match {
        case Some(scene) => {
          scene.stop();
          scene.step(); // do one more step to update all the fields to the current time
        }
        case _ => logger.info("No scene established, time controls are ignored!");
      }
    }
  };
  private val step = button(`type` := "button", name := "step", raw("&#x25B8;&#x25B8;")).render;
  step.onclick = (e: MouseEvent) => {
    if (editing) {
      Main.scene match {
        case Some(scene) => {
          updateScene(scene);
          scene.step();
        }
        case _ => logger.info("No scene established, time controls are ignored!");
      }
    }
  };

  private val updateInterval = Seconds(0.5).toMilliseconds;
  private var lastUpdate = Double.NegativeInfinity;

  private def updateScene(scene: TimeAnimatedScene) {
    scene.setOffset(fieldsToTime().to(J2000TT));
    val spd = speed.value match {
      case "s" => Seconds(1.0)
      case "m" => Minutes(1.0)
      case "h" => Hours(1.0)
      case "d" => Days(1.0)
    };
    scene.setSpeed(spd);
  }

  private def fieldsToTime(): TimeScale = {
    val years = Years(epoch.value match {
      case "BF" => -inputToDouble(yearField.value)
      case _    => inputToDouble(yearField.value)
    });
    val days = Days(inputToDouble(dayField.value));
    val hours = Hours(inputToDouble(hourField.value));
    val minutes = Minutes(inputToDouble(minuteField.value));
    val seconds = Seconds(inputToDouble(secondField.value));
    val t = years + days + hours + minutes + seconds;
    epoch.value match {
      case x @ ("AF" | "BF") => AFTT(t)
      case "J2000"           => J2000TT(t)
      case "JD"              => JulianDateTT(t)
      case "Unix"            => UnixTime(t)
      case x                 => logger.error(s"Unrecognized epoch ${x}"); JulianDateTT(t) // FIXME

    }
  }

  private def inputToDouble(in: String): Double = {
    try {
      in.toDouble
    } catch {
      case e: Throwable => logger.error(e.getMessage); 0.0
    }
  }

  def update(t: Time) {
    val tnow = Date.now();
    val tdiff = tnow - lastUpdate;
    if (((tdiff) > updateInterval) || editing) { // always update in editing mode
      val tscaled = J2000TT(t);
      val tselection = epoch.value match {
        case x @ ("AF" | "BF") => tscaled.to(AFTT)
        case "J2000"           => tscaled.t
        case "JD"              => tscaled.to(JulianDateTT)
        case "Unix"            => tscaled.to(UnixTime)
        case x                 => logger.error(s"Unrecognized epoch ${x}"); tscaled.t // FIXME
      }
      //logger.info(s"Updating to time t=${t.toSeconds} with is ${tscaled} or ${tselection.toSeconds} in ${epoch.value}")
      val yearsWithRest = TimeUtils.toFull(Years)(tselection);
      //logger.info(s"In years ${yearsWithRest._1} with rest ${yearsWithRest._2}")
      val daysWithRest = TimeUtils.toFull(Days)(yearsWithRest._2);
      //logger.info(s"In days ${daysWithRest._1} with rest ${daysWithRest._2}")
      val hoursWithRest = TimeUtils.toFull(Hours)(daysWithRest._2);
      //logger.info(s"In hours ${hoursWithRest._1} with rest ${hoursWithRest._2}")
      val minutesWithRest = TimeUtils.toFull(Minutes)(hoursWithRest._2);
      //logger.info(s"In minutes ${minutesWithRest._1} with rest ${minutesWithRest._2}")
      val secondsWithRest = TimeUtils.toFull(Seconds)(minutesWithRest._2);
      //logger.info(s"In seconds ${secondsWithRest._1} with rest ${secondsWithRest._2}")
      val years = epoch.value match {
        case "BF" => -yearsWithRest._1
        case _    => yearsWithRest._1
      }
      yearField.value = years.toString();
      dayField.value = daysWithRest._1.toString();
      hourField.value = hoursWithRest._1.toString();
      minuteField.value = minutesWithRest._1.toString();
      secondField.value = secondsWithRest._1.toString();
      //logger.info(s"Updated values to y=${years.toString()}, d=${daysWithRest._1.toString()}, h=${hourField.value}, m=${minuteField.value}, s=${secondField.value}")

      lastUpdate = tnow;
    }
  }

  private def editMode(on: Boolean) = {
    if (on) {
      timeFields.foreach { f => f.disabled = false }
      stop.disabled = true;
      play.disabled = false;
      editing = true;
    } else {
      timeFields.foreach { f => f.disabled = true }
      stop.disabled = false;
      play.disabled = true;
      editing = false;
    }
  }

  val block = div(
    id := "time",
    table(
      tr(th("Time"), th("Day"), th("Year"), th("Epoch"), th("")),
      tr(td(hourField, ":", minuteField, ":", secondField), td(dayField), td(yearField), td(epoch), td("TT"))),
    UI.label("Speed"), speed, play, stop, step).render;
}
