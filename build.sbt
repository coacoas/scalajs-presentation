enablePlugins(ScalaJSPlugin)

scalaVersion := "2.11.7"
scalaJSStage in Global := FastOptStage
skip in packageJSDependencies := false

libraryDependencies +=0 "be.doeraene" %%% "scalajs-jquery" % "0.8.0"
