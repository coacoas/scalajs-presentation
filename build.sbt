enablePlugins(ScalaJSPlugin)

scalaVersion := "2.11.7"
scalaJSStage in Global := FastOptStage
skip in packageJSDependencies := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
