object AcceptedConditionals {
  def value(flag: Boolean): Int = if (flag) 1 else 2

  def action(flag: Boolean): Unit = {
    if (flag) println("yes")
    if (flag) {
      println("only then")
    }
    if (flag) {
      println("yes")
    } else if (!flag) {
      println("no")
    }
    if (flag) println("unit") else ()
  }

  def suppressed(flag: Boolean): Int = {
    // scalafix:off MultilineIfBraces
    if (flag) 1
    else 2
    // scalafix:on MultilineIfBraces
  }

  def localImport(): Unit = {
    import scala.collection.mutable.ArrayBuffer
    val unused = ArrayBuffer.empty[Int]
    ()
  }
}
