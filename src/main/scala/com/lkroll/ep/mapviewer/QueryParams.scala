package com.lkroll.ep.mapviewer

import java.net.URI
import scala.util.Try

trait Paramable {
  def toParam: (String, String);
}

case class TrivialParamable(t: (String, String)) extends Paramable {
  def toParam: (String, String) = t;
}

class QueryParams private (val params: Map[String, String]) {

  def apply(name: String): String = params(name);

  def get(name: String): Option[String] = params.get(name);

  def asInt(name: String): Int = apply(name).toInt;
  def getInt(name: String): Option[Int] = get(name).flatMap(str => Try(str.toInt).toOption);

  def asLong(name: String): Long = apply(name).toLong;
  def getLong(name: String): Option[Long] = get(name).flatMap(str => Try(str.toLong).toOption);

  def asFloat(name: String): Float = apply(name).toFloat;
  def getFloat(name: String): Option[Float] = get(name).flatMap(str => Try(str.toFloat).toOption);

  def asDouble(name: String): Double = apply(name).toDouble;
  def getDouble(name: String): Option[Double] = get(name).flatMap(str => Try(str.toDouble).toOption);

  def toURI(baseURI: String): String = {
    s"$baseURI?${params.map{ case (key, value) => s"$key=$value" }.mkString("&")}"
  }

  def +(kv: (String, String)): QueryParams = {
    val newParams = params + kv;
    QueryParams(newParams)
  }

  def ++(m: Map[String, String]): QueryParams = {
    val newParams = params ++ m;
    QueryParams(newParams)
  }

  def ++(other: QueryParams) = {
    val newParams = params ++ other.params;
    QueryParams(newParams)
  }

  override def toString(): String = s"QueryParams(${params.mkString(", ")})"
}

object QueryParams {
  def apply(): QueryParams = new QueryParams(Map.empty);
  def apply(params: Map[String, String]): QueryParams = new QueryParams(params);
  def apply(params: Paramable*): QueryParams = new QueryParams(params.map(_.toParam).toMap);
  def apply(params: String): QueryParams = {
    if (params == null) { // ugly Java code -.-
      return new QueryParams(Map.empty)
    }
    val paramGroups = params.split("&");
    val paramList = paramGroups.flatMap { paramGroup =>
      val param = paramGroup.split("=");
      if (param.length == 2) {
        Some((param(0) -> param(1)))
      } else {
        None
      }
    }
    new QueryParams(paramList.toMap)
  }

  def fromURI(uri: String): QueryParams = fromURI(new URI(uri));
  def fromURI(uri: URI): QueryParams = apply(uri.getQuery);

  def replace(uriS: String, parameter: String, newValue: String): String = {
    val uri = new URI(uriS);
    //    // TODO do this properly
    //    s"${uri.getPath}?$parameter=$newValue"
    val qp = fromURI(uri);
    val newQp = qp + (parameter -> newValue);
    newQp.toURI(uri.getPath)
  }
}
