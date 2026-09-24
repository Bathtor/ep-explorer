addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.22.0")

// Selenium is published for Scala 2.13; reuse the Scala.js plugin's JS environment interfaces.
libraryDependencies += ("org.scala-js" % "scalajs-env-selenium_2.13" % "1.1.1")
  .exclude("org.scala-js", "scalajs-js-envs_2.13")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.13.2")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")
