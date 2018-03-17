name := "panda-api"
organization := "io.nyancode"
version := "0.1.0"
scalaVersion := "2.12.3"

lazy val root = project in file(".") enablePlugins PlayScala

val macWireVersion = "2.3.0"
val scalaTestVersion = "3.1.2"
val sangriaVersion = "1.3.0"
val sangriaJsonVersion = "1.0.4"
val jacksonVersion = "2.9.4"
val scalazVersion = "7.2.20"
val scalaMockVersion = "3.5.0"

libraryDependencies += "com.softwaremill.macwire" %% "macros" % macWireVersion % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "util" % macWireVersion
libraryDependencies += "com.softwaremill.macwire" %% "proxy" % macWireVersion
libraryDependencies += "org.sangria-graphql" %% "sangria" % sangriaVersion
libraryDependencies += "org.sangria-graphql" %% "sangria-play-json" % sangriaJsonVersion
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestVersion % Test
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
libraryDependencies += "org.scalaz" %% "scalaz-core" % scalazVersion
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test
