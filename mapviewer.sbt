enablePlugins(ScalaJSPlugin)
enablePlugins(BuildInfoPlugin)

name := "EPMapViewer"

organization := "com.lkroll.ep"

version := "0.6.2"

scalaVersion := "2.12.10"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scala-js" %% "scalajs-dom" % "0.9.+"
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.6.+"
libraryDependencies += "org.scala-js" %% "scalajs-java-time" % "0.2.+"
libraryDependencies += "com.outr" %% "scribe" % "2.5.+"
libraryDependencies += "org.denigma" %% "threejs-facade" % "0.0.88-0.1.8"
libraryDependencies += "org.typelevel" %% "squants" % "1.3.+"
libraryDependencies += "com.lkroll.common" %% "common-data-tools" % "1.2.+"

scalaJSUseMainModuleInitializer := true
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.lkroll.ep.mapviewer.build"
