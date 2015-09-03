enablePlugins(ScalaJSPlugin)

scalaVersion := "2.11.7"
scalaJSStage in Global := FastOptStage
skip in packageJSDependencies := false

libraryDependencies ++= Seq(
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)

jsDependencies += RuntimeDOM
testFrameworks += new TestFramework("utest.runner.Framework")
