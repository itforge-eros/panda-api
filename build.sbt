name := "panda-api"
organization := "io.itforge"
version := "1.0.1"
scalaVersion := "2.12.5"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(JavaServerAppPackaging)
  .enablePlugins(DockerPlugin)

val macWireVersion = "2.3.0"
val scalaTestVersion = "3.1.2"
val sangriaVersion = "1.4.0"
val sangriaJsonVersion = "1.0.4"
val sangriaCirceVersion = "1.2.1"
val jacksonVersion = "2.9.4"
val scalazVersion = "7.2.20"
val scalaMockVersion = "3.5.0"
val circeJacksonVersion = "0.9.0"
val playCirceVersion = "2609.1"
val anormVersion = "2.6.0-M1"
val scalaLoggingVersion = "3.8.0"
val monocleVersion = "1.5.0"
val circeVersion = "0.9.1"
val postgresqlVersion = "9.4.1208"
val henkanVersion = "0.6.1"
val jwtScalaVersion = "0.16.0"
val doobieVersion = "0.5.2"
val scalaKafkaClientVersion = "1.1.1"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  "com.typesafe.play" %% "anorm" % anormVersion,
  "org.postgresql" % "postgresql" % postgresqlVersion,
  "org.sangria-graphql" %% "sangria" % sangriaVersion,
  "org.sangria-graphql" %% "sangria-play-json" % sangriaJsonVersion,
  "org.sangria-graphql" %% "sangria-circe" % sangriaCirceVersion,
  "com.softwaremill.macwire" %% "macros" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % "provided",
  "com.softwaremill.macwire" %% "util" % macWireVersion,
  "com.softwaremill.macwire" %% "proxy" % macWireVersion,
  "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-jackson29" % circeJacksonVersion,
  "com.dripower" %% "play-circe" % playCirceVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestVersion % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "com.kailuowang" %% "henkan-convert" % henkanVersion,
  "com.kailuowang" %% "henkan-optional" % henkanVersion,
  "com.pauldijou" %% "jwt-core" % jwtScalaVersion,
  "com.pauldijou" %% "jwt-circe" % jwtScalaVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "net.cakesolutions" %% "scala-kafka-client" % scalaKafkaClientVersion
)

scalacOptions += "-Ypartial-unification"

packageName in Docker := "panda-api"
dockerRepository := Some("docker.io")
dockerUsername := Some("kavinvin")
dockerUpdateLatest := true
dockerExposedPorts := Seq(9000)

fork in run := true
parallelExecution in Test := true
