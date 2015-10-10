import com.sun.scenario.Settings

scalaVersion := "2.11.7"

lazy val root = Project("scala-js", file("."))
  .enablePlugins(ScalaJSPlugin)

libraryDependencies ++=
  Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.1",
    "be.doeraene" %%% "scalajs-jquery" % "0.8.1",
    "com.github.japgolly.scalajs-react" %%% "core" % "0.9.2") ++
    Seq(
      "com.github.japgolly.nyaya" %%% "nyaya-core",
      "com.github.japgolly.nyaya" %%% "nyaya-test")
      .map(_ % "0.5.11" % "test")

jsDependencies ++= Seq(
  RuntimeDOM,
  "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React")

testFrameworks += new TestFramework("utest.runner.Framework")

skip in packageJSDependencies := false
scalaJSStage in Global := FastOptStage
