import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)
workbenchSettings

name := "Map Viewer"

organization := "com.larskroll.ep"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

//resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases")
resolvers += Resolver.mavenLocal

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.0"
libraryDependencies += "org.scala-js" %%% "scalajs-java-time" % "0.2.0"
libraryDependencies += "com.outr.scribe" %%% "scribe" % "1.2.4"
libraryDependencies += "org.denigma" %%% "threejs-facade" % "0.0.82-0.1.7"
//libraryDependencies += "org.querki" %%% "jquery-facade" % "1.0-RC6"
libraryDependencies += "com.squants"  %%% "squants"  % "0.6.2"

//jsDependencies += "org.webjars" % "jquery" % "3.1.0" / "jquery.js" minified "jquery.min.js"
jsDependencies += "org.webjars" % "three.js" % "r82" / "three.js" minified "three.min.js"
jsDependencies += ProvidedJS / "CopyShader.js" dependsOn "three.js"
jsDependencies += ProvidedJS / "FXAAShader.js" dependsOn "three.js"
jsDependencies += ProvidedJS / "Stats.js" dependsOn "three.js"
jsDependencies += ProvidedJS / "OrbitControls.js" dependsOn "three.js"
jsDependencies += ProvidedJS / "EffectComposer.js" dependsOn "CopyShader.js"
jsDependencies += ProvidedJS / "CSS3DRenderer.js" dependsOn "EffectComposer.js"
jsDependencies += ProvidedJS / "ClearPass.js" dependsOn "EffectComposer.js"
jsDependencies += ProvidedJS / "RenderPass.js" dependsOn "EffectComposer.js"
jsDependencies += ProvidedJS / "TexturePass.js" dependsOn "EffectComposer.js"
jsDependencies += ProvidedJS / "ShaderPass.js" dependsOn "EffectComposer.js"



persistLauncher in Compile := true
persistLauncher in Test := false
skip in packageJSDependencies := false

bootSnippet := "com.larskroll.ep.mapviewer.Main().main();"
refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)
//localUrl := ("lkroll.sics.se", 12345)
localUrl := ("192.168.0.102", 12345)
//localUrl := ("192.168.178.208", 12345)
//localUrl := ("localhost", 12345)