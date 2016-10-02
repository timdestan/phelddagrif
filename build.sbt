name := """Phelddagrif"""

version := "0.1"

scalaVersion := "2.11.8"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % "0.3.0",
  "io.circe" %% "circe-generic" % "0.3.0",
  "io.circe" %% "circe-parser" % "0.3.0",
  "org.typelevel" %% "cats" % "0.4.1",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

mainClass in (Compile, run) := Some("phelddagrif.importer.MtgJsonImporter")
