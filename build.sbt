lazy val phelddagrif = (project in file(".")).settings(
  name := """Phelddagrif""",
  version := "0.1",
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq(
    "-feature",
    "-language:_",
    "-deprecation",
    "-Ypartial-unification"
  ),
  libraryDependencies ++= Seq(
    "com.lihaoyi"   %% "fastparse"     % "0.4.2",
    "io.circe"      %% "circe-core"    % "0.7.0",
    "io.circe"      %% "circe-generic" % "0.7.0",
    "io.circe"      %% "circe-parser"  % "0.7.0",
    "org.typelevel" %% "cats"          % "0.9.0",
    "com.lihaoyi"   %% "utest"         % "0.6.0" % "test"
  ),
  testFrameworks := Seq(new TestFramework("utest.runner.Framework"))
)
