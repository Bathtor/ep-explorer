package com.lkroll.ep.mapviewer

import com.lkroll.ep.mapviewer.datamodel.AstronomicalObject;

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

case class VerificationError(msg: String, parent: Throwable) extends Exception(msg, parent);

trait ParamConverter[T] {
  def fromParam(s: String): T;
  def toParam(t: T): String = t.toString();
}

case class ForwardConverter[T](from: String => T) extends ParamConverter[T] {
  override def fromParam(s: String): T = from(s);
}

case class BiConverter[T](from: String => T, to: T => String) extends ParamConverter[T] {
  override def fromParam(s: String): T = from(s);
  override def toParam(t: T): String = to(t);
}

abstract class Options(val params: QueryParams) {

  case class Opt[T](key: String,
                    converter: ParamConverter[T],
                    default: () => Option[T] = () => None,
                    required: Boolean = false) {
    def apply(): T = this.get.get;
    def get: Option[T] = {
      cache.get(key) match {
        // Each key must retain its T; erasure may defer a bad cast until the value is used.
        case Some(value) => Some(value.asInstanceOf[T])
        case None => {
          params.get(key) match {
            case Some(s) => {
              val t = converter.fromParam(s);
              cache += (key -> t);
              Some(t)
            }
            case None => {
              default()
            }
          }
        }
      }
    }
    def apply(t: T): Paramable = TrivialParamable(key -> converter.toParam(t));
  }

  private val cache = mutable.HashMap.empty[String, Any];
  private var opts = List.empty[Opt[_]];

  private def addOpt[T](o: Opt[T]): Opt[T] = {
    opts ::= o;
    o
  }

  protected def opt[T](key: String, default: => Option[T] = None, required: Boolean = false)(
      implicit converter: ParamConverter[T]
  ): Opt[T] = addOpt(Opt(key, converter, () => default, required));

  def verify(): List[Failure[Unit]] = {
    val res = opts.map { o =>
      val loaded = Try(o.get);
      val checked = loaded.map(
        r =>
          if (o.required) {
            o.get; ()
          } else {
            ()
          }
      );
      checked.transform(
        _ => Success(()),
        (f: Throwable) =>
          Failure(VerificationError(s"Could not verify option '${o.key}' for supplied value '${params.get(o.key)}'", f))
      );
    };
    res.collect { case f: Failure[Unit] => f };
  }

  implicit val str2bool: ParamConverter[Boolean] = ForwardConverter(_.toBoolean);
  implicit val str2view: ParamConverter[View] = BiConverter(s => View.fromLabel(s).get, _.label);
  private def findObject(s: String): AstronomicalObject = {
    data.findObjectForName(s) match {
      case Some(ao) => ao
      case None     => throw new java.util.NoSuchElementException(s"No astronomical object with name $s was found!")
    }
  }
  implicit val str2ao: ParamConverter[AstronomicalObject] = BiConverter(findObject, _.name);
}

object MapOptions {
  def default: MapOptions = new MapOptions(QueryParams());
  def apply(params: QueryParams): MapOptions = new MapOptions(params);
}

class MapOptions(_params: QueryParams) extends Options(_params) {
  val debug = opt[Boolean]("debug", default = Some(false), required = true);
  val view = opt[View]("view", default = Some(View.System), required = true);
  val target = opt[AstronomicalObject]("target");
  val tracking = opt[AstronomicalObject]("tracking");
}
