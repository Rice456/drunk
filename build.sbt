// ··· Project Settings ···

// scalafixSettings

// ··· Project Info ···

name := "drunk"

description := "A simple GraphQL client on top of Sangria, Akka HTTP and Circe"

scalaVersion := "2.12.15"

version := "2.5.0"

publishMavenStyle := true

//For M1 architecture
Global / serverConnectionType := ConnectionType.Tcp

// ··· Project Options ···

lazy val root = (project in file("."))

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-unchecked",
  "-deprecation",
  "-Ymacro-expand:normal"
)

// ··· Project Repositories ···

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"))

// ··· Project Dependencies ···
val sangriaV        = "1.4.+"
val sangriaCirceV   = "1.2.1"
val akkaHttpV       = "10.1.+"
val akkaHttpCircleV = "1.22.+"
val circeV          = "0.10.+"
val slf4JV          = "1.7.25"
val logbackV        = "1.2.3"
val scalatestV      = "3.0.5"

libraryDependencies ++= Seq(
  // --- GraphQL --
  "org.sangria-graphql" %% "sangria"          % sangriaV,
  "org.sangria-graphql" %% "sangria-circe"    % sangriaCirceV,
  // --- Akka --
  "com.typesafe.akka"   %% "akka-http"        % akkaHttpV,
  "de.heikoseeberger"   %% "akka-http-circe"  % akkaHttpCircleV,
  // --- Utils ---
  "io.circe"            %% "circe-generic"    % circeV,
  "io.circe"            %% "circe-parser"     % circeV,
  // --- Logger ---
  "org.slf4j"           %  "slf4j-api"        % slf4JV,
  "ch.qos.logback"      %  "logback-classic"  % logbackV        % Test,
  // --- Testing ---
  "com.typesafe.akka"   %% "akka-http-testkit"  % akkaHttpV     % Test,
  "org.scalatest"       %% "scalatest"          % scalatestV    % Test
)

// ··· Testing Configuration ···

fork in (Test, run) := false

scalacOptions in Test ++= Seq("-Yrangepos")

/**
 * Maven specific settings for publishing to Maven central.
 */
credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")

ThisBuild / organization := "io.github.rice456"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/Rice456/drunk"),
    "scm:git@github.com:Rice456/drunk.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "rice456",
    name = "Joe liu",
    email = "joe.liu@gummicube.com",
    url = url("https://github.com/Rice456/drunk")
  )
)

ThisBuild / description := "drunk"
ThisBuild / licenses := List(
  "Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")
)
ThisBuild / homepage := Some(url("https://github.com/Rice456/drunk"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
ThisBuild / publishArtifact in Test := false