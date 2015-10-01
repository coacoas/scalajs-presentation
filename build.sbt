enablePlugins(ScalaJSPlugin)

workbenchSettings

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.1",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test" )

jsDependencies ++= Seq(
  RuntimeDOM)

testFrameworks += new TestFramework("utest.runner.Framework")

skip in packageJSDependencies := false

scalaJSStage in Global := FastOptStage

bootSnippet := "presentation.Demo.main()"

refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)


