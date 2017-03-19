name := """Phelddagrif"""

version := "0.1"

scalaVersion := "2.12.0"

scalacOptions += "-feature"
scalacOptions += "-language:higherKinds"

val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fastparse" % "0.4.2",

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.typelevel" %% "cats" % "0.9.0",

  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

mainClass in (Compile, run) := Some("phelddagrif.importer.MtgJsonImporter")
