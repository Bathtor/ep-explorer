package com.lkroll.ep.mapviewer.utils

import java.util.Comparator

/** Finds the two sorted samples around a key so callers can interpolate their values.
  * Circular caches return the last and first samples across the boundary; the caller
  * knows the full period needed to measure that wrapping interval.
  */
class PosCache[T](private val samples: Array[(Double, T)], val circular: Boolean) {

  val firstSample = samples.head;
  val firstKey = firstSample._1;
  val lastSample = samples.last;
  val lastKey = lastSample._1;
  val keySpan = lastKey - firstKey;

  def neighbours(queryKey: Double): ((Double, T), (Double, T)) = {
    if (samples.length == 1) {
      (firstSample, firstSample)
    } else if ((queryKey < firstKey) || (lastKey < queryKey)) {
      if (circular) {
        (lastSample, firstSample)
      } else {
        throw new IndexOutOfBoundsException(s"Needle $queryKey was not in range [$firstKey, $lastKey]!");
      }
    } else {
      // The orbit samples are evenly spaced. Estimate the index from the fraction
      // of the key span, then scan to the actual sample below the query.
      var lowerIndex = Math.min(samples.length - 1, Math.floor(samples.length * (queryKey - firstKey) / keySpan).toInt)
      while (lowerIndex > 0 && samples(lowerIndex)._1 > queryKey) lowerIndex -= 1
      while (lowerIndex + 1 < samples.length && samples(lowerIndex + 1)._1 <= queryKey) lowerIndex += 1

      if (lowerIndex == samples.length - 1) {
        // At the final key, circular caches wrap to the first sample. Linear
        // caches have no following sample, so use the preceding pair instead.
        if (circular) {
          (lastSample, firstSample)
        } else {
          (samples(lowerIndex - 1), lastSample)
        }
      } else {
        (samples(lowerIndex), samples(lowerIndex + 1))
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

  def keyComparator[T]: Comparator[(Double, T)] = new Comparator[(Double, T)] {
    override def compare(o1: (Double, T), o2: (Double, T)): Int = {
      Ordering.Double.TotalOrdering.compare(o1._1, o2._1)
    }
  };

  class Builder[T](size: Int, circular: Boolean) {
    private val samples: Array[(Double, T)] = new Array[(Double, T)](size);

    private var nextIndex: Int = 0;
    def +=(t: (Double, T)): Unit = {
      if (nextIndex >= size) {
        throw new IndexOutOfBoundsException(s"Index was $nextIndex >= $size (size)!");
      }
      samples(nextIndex) = t;
      nextIndex += 1;
    }
    def result(): PosCache[T] = {
      assert(nextIndex == size, "Called result before builder was filled!");
      java.util.Arrays.sort(samples, keyComparator[T]);
      new PosCache(samples, circular)
    }
  }
}
