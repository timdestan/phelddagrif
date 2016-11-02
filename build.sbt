name := """Phelddagrif"""

version := "0.1"

scalaVersion := "2.11.8"

scalacOptions += "-feature"

val circeVersion = "0.5.1"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fastparse" % "0.4.1",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.typelevel" %% "cats" % "0.7.0",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

mainClass in (Compile, run) := Some("phelddagrif.importer.MtgJsonImporter")
