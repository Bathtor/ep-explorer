object RejectedConditionals {
  def value(flag: Boolean): Int = {
    if (flag) 1
    else 2
  }

  def action(flag: Boolean): Unit = {
    if (flag)
      println("yes")
    if (flag) {
      println("yes")
    } else ()
  }

  def nested(a: Boolean, b: Boolean): Int = {
    if (a) {
      if (b) 1 // the inner branch spans two lines
      else 2
    } else {
      3
    }
  }
}
