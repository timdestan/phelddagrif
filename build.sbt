name := """Phelddagrif"""

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions += "-feature"

resolvers += Resolver.sonatypeRepo("snapshots")


libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % "0.3.0",
  "io.circe" %% "circe-generic" % "0.3.0",
  "io.circe" %% "circe-parser" % "0.3.0",

  "org.typelevel" %% "cats" % "0.4.1",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
