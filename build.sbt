name := "panda-api"
organization := "io.nyancode"
version := "0.1.0"
scalaVersion := "2.12.3"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

lazy val macWireVersion = "2.3.0"
lazy val scalaTestVersion = "3.1.2"

libraryDependencies += "com.softwaremill.macwire" %% "macros" % macWireVersion % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "util" % macWireVersion
libraryDependencies += "com.softwaremill.macwire" %% "proxy" % macWireVersion
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
