enablePlugins(WorkbenchPlugin)
enablePlugins(ScalaJSPlugin)
enablePlugins(BuildInfoPlugin)

name := "EPMapViewer"

organization := "com.lkroll.ep"

version := "0.6.2"

scalaVersion := "2.12.10"

//resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases")
resolvers += Resolver.mavenLocal
resolvers += Resolver.bintrayRepo("lkrollcom", "maven")

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.+"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.+"
libraryDependencies += "org.scala-js" %%% "scalajs-java-time" % "0.2.+"
libraryDependencies += "com.outr" %%% "scribe" % "2.5.+"
libraryDependencies += "org.denigma" %%% "threejs-facade" % "0.0.88-0.1.8"
libraryDependencies += "org.typelevel" %%% "squants" % "1.3.+"
libraryDependencies += "com.lkroll.common" %%% "common-data-tools" % "1.2.+"

jsDependencies += "org.webjars" % "three.js" % "r88" / "three.js" minified "three.min.js"
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

scalaJSUseMainModuleInitializer := true
skip in packageJSDependencies := false
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.lkroll.ep.mapviewer.build"

//bootSnippet := "com.larskroll.ep.mapviewer.Main().main();"
//refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)
//localUrl := ("lkroll.sics.se", 12345)
localUrl := ("192.168.0.106", 12345)
//localUrl := ("10.112.11.78", 12345)
//localUrl := ("localhost", 12345)
