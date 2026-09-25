enablePlugins(ScalaJSPlugin)
enablePlugins(BuildInfoPlugin)

name := "EPMapViewer"

organization := "com.lkroll.ep"

version := "0.6.2"

scalaVersion := "2.13.18"

semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision
scalacOptions += "-Wunused:imports"

// Local Scalafix rules live in src/scalafix/scala and do not enter the Scala.js application.
libraryDependencies += ("ch.epfl.scala" % "scalafix-core_2.13" % _root_.scalafix.sbt.BuildInfo.scalafixVersion) % ScalafixConfig
// The Scala.js compiler plugin is inherited by this configuration, but local rules run on the JVM.
ScalafixConfig / scalacOptions := Nil

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scala-js" %% "scalajs-dom" % "2.3.0"
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.13.1"
libraryDependencies += "org.scala-js" %% "scalajs-java-time" % "0.2.+"
libraryDependencies += "org.scala-js" %% "scalajs-java-securerandom" % "1.0.0"
libraryDependencies += "com.outr" %% "scribe" % "3.15.2"
libraryDependencies += "org.typelevel" %% "squants" % "1.8.3"
libraryDependencies += "com.lkroll" %% "common-data-tools" % "1.3.3"
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.20" % Test

scalaJSUseMainModuleInitializer := true
// Vite resolves the app's @JSImport facades from ES module output.
scalaJSLinkerConfig ~= (_.withModuleKind(org.scalajs.linker.interface.ModuleKind.ESModule))
// Selenium opens its test harness from file://, where Safari rejects ES modules from another file (origin null).
// Pure Scala.js tests use a classic script; tests needing JS imports will need a served, bundled harness.
Test / scalaJSLinkerConfig ~= (_.withModuleKind(org.scalajs.linker.interface.ModuleKind.NoModule))
Test / parallelExecution := false
Test / jsEnv := {
  if (sys.props("os.name").startsWith("Mac OS")) {
    new org.scalajs.jsenv.selenium.SeleniumJSEnv(new org.openqa.selenium.safari.SafariOptions())
  } else {
    new org.scalajs.jsenv.nodejs.NodeJSEnv()
  }
}
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.lkroll.ep.mapviewer.build"
