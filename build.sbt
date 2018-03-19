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
val circeVersion = "0.9.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.softwaremill.macwire" %% "macros" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "util" % macWireVersion,
  "com.softwaremill.macwire" %% "proxy" % macWireVersion,
  "org.sangria-graphql" %% "sangria" % sangriaVersion,
  "org.sangria-graphql" %% "sangria-play-json" % sangriaJsonVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestVersion % Test,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test,
  "org.sangria-graphql" %% "sangria-circe" % "1.2.1",
  "com.dripower" %% "play-circe" % "2609.1",
  "io.circe" %% "circe-jackson29" % "0.9.0"
)
