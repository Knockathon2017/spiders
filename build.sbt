name := """knockathon"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  ws,
  specs2 % Test,
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.5.2",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.5.3"

)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


scalacOptions in ThisBuild ++= Seq("-feature", "-language:postfixOps")

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator