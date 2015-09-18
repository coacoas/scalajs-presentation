enablePlugins(ScalaJSPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)

jsDependencies += RuntimeDOM

testFrameworks += new TestFramework("utest.runner.Framework")

skip in packageJSDependencies := false

scalaJSStage in Global := FastOptStage
persistLauncher in Compile := true
persistLauncher in Test := false
