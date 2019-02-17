package com.lkroll.ep.mapviewer.utils

import java.util.Comparator

class PosCache[T](
  private val data: Array[(Double, T)],
  val circular:     Boolean) {

  val comp = PosCache.comp[T];

  val head = data.head;
  val headKey = head._1;
  val last = data.last;
  val lastKey = last._1;
  val range = lastKey - headKey;

  def neighbours(needle: Double): ((Double, T), (Double, T)) = {
    if ((needle < headKey) || (lastKey < needle)) {
      if (circular) {
        (last, head)
      } else {
        throw new IndexOutOfBoundsException(s"Needle $needle was not in range [$headKey, $lastKey]!");
      }
    } else {
      // assume things are evenly spaced
      val inRange = needle - headKey;
      val relRange = inRange / range;
      var pos = Math.floor(data.length * relRange).toInt;
      if (data(pos)._1 > needle) {
        while ((pos > 0) && (data(pos)._1 > needle)) { // scan down...should be close by
          pos -= 1;
        }
        //assert(data(pos)._1 <= needle);
        if (pos + 1 > data.length) {
          (data(pos), data(pos))
        } else {
          //assert(data(pos + 1)._1 >= needle);
          (data(pos), data(pos + 1))
        }
      } else { // scan up...should be close by
        while ((pos < data.length) && (data(pos)._1 < needle)) { // scan up...should be close by
          pos += 1;
        }
        //assert(data(pos)._1 >= needle);
        if (pos - 1 < 0) {
          (data(pos), data(pos))
        } else {
          //assert(data(pos - 1)._1 <= needle);
          (data(pos - 1), data(pos))
        }
      }
    }
  }
}

object PosCache {
  def newBuilder[T](size: Int, circular: Boolean = false): Builder[T] = new Builder[T](size, circular);
  def fill[T](size: Int, fill: Int => (Double, T), circular: Boolean = false): PosCache[T] = {
    val b = new Builder[T](size, circular);
    (0 until size) foreach { i =>
      b += fill(i);
    }
    b.result()
  }

  def comp[T]: Comparator[(Double, T)] = new Comparator[(Double, T)] {
    override def compare(o1: (Double, T), o2: (Double, T)): Int = {
      Ordering.Double.compare(o1._1, o2._1)
    }
  };

  class Builder[T](size: Int, circular: Boolean) {
    private val data: Array[(Double, T)] = new Array[(Double, T)](size);

    private var i: Int = 0;
    def +=(t: (Double, T)): Unit = {
      if (i >= size) {
        throw new IndexOutOfBoundsException(s"Index was $i >= $size (size)!");
      }
      data(i) = t;
      i += 1;
    }
    def result(): PosCache[T] = {
      assert(i == size, "Called result before builder was filled!");
      java.util.Arrays.sort(data, comp[T]);
      new PosCache(data, circular)
    }
  }
}
