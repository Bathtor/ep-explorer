enablePlugins(ScalaJSPlugin)
enablePlugins(BuildInfoPlugin)

name := "EPMapViewer"

organization := "com.lkroll.ep"

version := "0.6.2"

scalaVersion := "2.13.18"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scala-js" %% "scalajs-dom" % "2.3.0"
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.13.1"
libraryDependencies += "org.scala-js" %% "scalajs-java-time" % "0.2.+"
libraryDependencies += "org.scala-js" %% "scalajs-java-securerandom" % "1.0.0"
libraryDependencies += "com.outr" %% "scribe" % "3.15.2"
libraryDependencies += "org.typelevel" %% "squants" % "1.8.3"
libraryDependencies += "com.lkroll" %% "common-data-tools" % "1.3.3"

scalaJSUseMainModuleInitializer := true
Test / jsEnv := {
  if (sys.props("os.name").startsWith("Mac OS")) {
    new org.scalajs.jsenv.selenium.SeleniumJSEnv(new org.openqa.selenium.safari.SafariOptions())
  } else {
    new org.scalajs.jsenv.nodejs.NodeJSEnv()
  }
}
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.lkroll.ep.mapviewer.build"
