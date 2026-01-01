val scala3Version = "3.3.7"

lazy val root = project
  .in(file("."))
  .settings(
    name := "claude-skills-test",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test
  )
