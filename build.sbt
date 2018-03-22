name := "panda-api"
organization := "io.nyancode"
version := "0.1.0"
scalaVersion := "2.12.3"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(JavaServerAppPackaging)

val macWireVersion = "2.3.0"
val scalaTestVersion = "3.1.2"
val sangriaVersion = "1.3.0"
val sangriaJsonVersion = "1.0.4"
val sangriaCirceVersion = "1.2.1"
val jacksonVersion = "2.9.4"
val scalazVersion = "7.2.20"
val scalaMockVersion = "3.5.0"
val circeJacksonVersion = "0.9.0"
val playCirceVersion = "2609.1"
val anormVersion = "2.6.0-M1"
val scalaLoggingVersion = "3.8.0"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  jdbc
    exclude("com.h2database", "h2")
    exclude("com.jolbox", "bonecp"),
  evolutions,
  "com.typesafe.play" %% "anorm" % anormVersion,
  "com.softwaremill.macwire" %% "macros" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "util" % macWireVersion,
  "com.softwaremill.macwire" %% "proxy" % macWireVersion,
  "org.postgresql" % "postgresql" % "9.4.1208",
  "org.sangria-graphql" %% "sangria" % sangriaVersion,
  "org.sangria-graphql" %% "sangria-play-json" % sangriaJsonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "org.sangria-graphql" %% "sangria-circe" % sangriaCirceVersion,
  "com.dripower" %% "play-circe" % playCirceVersion,
  "io.circe" %% "circe-jackson29" % circeJacksonVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestVersion % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
)

parallelExecution in Test := true
