enablePlugins(ScalaJSPlugin)

workbenchSettings

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.monifu"  %%% "monifu" % "1.0-RC2",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.9.2",
  "com.github.japgolly.fork.scalaz" %%% "scalaz-core" % "7.1.2",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)

jsDependencies ++= Seq(
  RuntimeDOM,
  "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React")


testFrameworks += new TestFramework("utest.runner.Framework")

skip in packageJSDependencies := false

scalaJSStage in Global := FastOptStage

bootSnippet := "presentation.Demo.main()"

refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)


