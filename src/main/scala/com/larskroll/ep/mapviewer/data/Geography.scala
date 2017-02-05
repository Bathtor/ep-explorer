package com.larskroll.ep.mapviewer.data

import java.util.UUID;
import scala.language.implicitConversions
import squants.space._
import squants.time._
import org.denigma.threejs.{ Vector3, Matrix4 }
import ExtraUnits._

object Geography {
  sealed trait CardinalDirection {
    def symbol: String;
  }
  sealed trait AxisDirection extends CardinalDirection
  sealed trait EquatorialDirection extends CardinalDirection
  object North extends AxisDirection {
    override def symbol = "N"
    def apply(deg: Int, min: Int, sec: Int): Latitude = Latitude((deg, min, sec), this)
  }
  object East extends EquatorialDirection {
    override def symbol = "E"
    def apply(deg: Int, min: Int, sec: Int): Longitude = Longitude((deg, min, sec), this)
  }
  object South extends AxisDirection {
    override def symbol = "S"
    def apply(deg: Int, min: Int, sec: Int): Latitude = Latitude((deg, min, sec), this)
  }
  object West extends EquatorialDirection {
    override def symbol = "W"
    def apply(deg: Int, min: Int, sec: Int): Longitude = Longitude((deg, min, sec), this)
  }

  case class Latitude(value: Angle, direction: AxisDirection)
  case class Longitude(value: Angle, direction: EquatorialDirection)

  case class Position(val lat: Latitude, val lon: Longitude) {
    val (rightascension: Angle, declination: Angle) = {
      val delta = {
        if (lat.direction == North) {
          lat.value
        } else {
          -lat.value
        }
      };
      val alpha = {
        if (lon.direction == East) {
          lon.value
        } else {
          -lon.value
        }
      };
      (alpha.normalise, delta)
    }
    lazy val pretty: String = {
      val latD = lat.value.toDegrees;
      val latFullD = Math.floor(latD);
      val latM = (latD - latFullD) * 60.0; // degrees to minutes
      val lonD = lon.value.toDegrees;
      val lonFullD = Math.floor(lonD);
      val lonM = (lonD - lonFullD) * 60.0; // degrees to minutes
      f"${latFullD.toInt}%02dº$latM%0.3f'${lat.direction.symbol} ${lonFullD.toInt}%03dº$lonM%0.3f'${lon.direction.symbol}"
    }
  }

  implicit def pairToPos(p: Tuple2[Latitude, Longitude]): Position = Position(p._1, p._2);
  implicit def tripleToAngle(t: Tuple3[Int, Int, Int]): Angle = Degrees(t._1 + (t._2.toDouble / 60.0) + (t._3.toDouble / 3600.0))
  implicit class GeoAngle(value: Angle) {
    def N = Latitude(value, North);
    def S = Latitude(value, South);
    def E = Longitude(value, East);
    def W = Longitude(value, West);
  }
}

case class EquatorialPositionSnapshot(pos: Vector3, rot: Matrix4)

case class EquatorialPosition(val rotor: Rotating, val alpha: Angle, val delta: Angle, val r: Length) {
  def at(t: Time): EquatorialPositionSnapshot = {
    val rot = rotor.rotation.at(t);
    val pos = {
      val R = r.toKilometers;
      val x = alpha.cos * delta.cos * R;
      val z = -(alpha.sin * delta.cos * R);
      val y = delta.sin * R;
      new Vector3(x, y, z)
    };
    pos.applyMatrix4(rot.rotationMatrix);
    EquatorialPositionSnapshot(pos, rot.rotationMatrix)
  }
}

abstract class AtmosphericObject(val pos: Geography.Position, val height: Length, val location: Rotating, _name: String, _id: UUID) extends AstronomicalObject(_name, _id) {
  val position = EquatorialPosition(location, pos.rightascension, pos.declination, location.radius + height);
}

abstract class SurfaceObject(_pos: Geography.Position, _location: Rotating, _name: String, _id: UUID) extends AtmosphericObject(_pos, Meters(0.0), _location, _name: String, _id: UUID) {

}

class Settlement(_name: String, _id: UUID, _pos: Geography.Position, _location: Rotating, val size: Length,
                 val allegiance: Allegiance, val langs: Seq[Language], val industries: Seq[Industry])
    extends SurfaceObject(_pos, _location, _name: String, _id: UUID) {
  override def `type` = "Settlement";
}

class UndergroundSettlement(_name: String, _id: UUID, _pos: Geography.Position, _depth: Length, _location: Rotating,
                            val allegiance: Allegiance, val langs: Seq[Language], val industries: Seq[Industry])
    extends AtmosphericObject(_pos, -_depth, _location, _name: String, _id: UUID) {
  override def `type` = "Underground Settlement";
}

class PandoraGate(_name: String, _id: UUID, _pos: Geography.Position, _location: Rotating,
                  val allegiance: Allegiance)
    extends SurfaceObject(_pos, _location, _name: String, _id: UUID) {
  override def `type` = "Pandora Gate";
}

class Aerostat(_name: String, _id: UUID, _pos: Geography.Position, _height: Length, _location: Rotating,
               val allegiance: Allegiance, val langs: Seq[Language], val industries: Seq[Industry])
    extends AtmosphericObject(_pos, _height, _location, _name: String, _id: UUID) {
  override def `type` = "Aerostat";
}

class SyncOrbitStation(_name: String, _id: UUID, _pos: Geography.Position, _height: Length, _location: Rotating,
                       val allegiance: Allegiance, val langs: Seq[Language], val industries: Seq[Industry])
    extends AtmosphericObject(_pos, _height, _location, _name: String, _id: UUID) {
  override def `type` = "Synchronous Orbit Station";
}

class Bathyscaphe(_name: String, _id: UUID, _pos: Geography.Position, _depth: Length, _location: Rotating,
                  val allegiance: Allegiance, val langs: Seq[Language], val industries: Seq[Industry])
    extends AtmosphericObject(_pos, -_depth, _location, _name: String, _id: UUID) {
  override def `type` = "Bathyscaphe";
}
