name := """Phelddagrif"""

version := "0.1"

scalaVersion := "2.12.1"

scalacOptions ++= Seq(
  "-feature",
  "-language:_",
  "-deprecation",
  "-Ypartial-unification"
)

val catsVersion      = "0.9.0"
val circeVersion     = "0.7.0"
val fastparseVersion = "0.4.2"

val scalatestVersion = "3.0.0"

libraryDependencies ++= Seq(
  "com.lihaoyi"   %% "fastparse"     % fastparseVersion,
  "io.circe"      %% "circe-core"    % circeVersion,
  "io.circe"      %% "circe-generic" % circeVersion,
  "io.circe"      %% "circe-parser"  % circeVersion,
  "org.typelevel" %% "cats"          % catsVersion,
  "org.scalatest" %% "scalatest"     % scalatestVersion % "test"
)
