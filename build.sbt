name := "telegrambot4s"

version := "1.3.0"

scalaVersion := "2.11.8"

organization := "info.mukel"

scalacOptions ++= Seq("-feature", "-deprecation")

val akkaVersion = "2.4.12"
val akkaHttpVersion = "2.4.11"
val json4sVersion = "3.5.0"
val scalaTestVersion = "3.0.0"
val scalaCheckVersion = "1.13.4"
val scalaLoggingVersion = "3.5.0"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "org.json4s" %% "json4s-ext"     % json4sVersion,

  "com.typesafe.akka" %% "akka-http-core"         % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream"            % akkaVersion,

  "org.scalatest"  %% "scalatest"  % scalaTestVersion  % "test",
  "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test",

  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "org.slf4j" % "slf4j-api" % "1.7.21"
)

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>https://github.com/mukel/telegrambot4s</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:mukel/telegrambot4s.git</url>
    <connection>scm:git:git@github.com:mukel/telegrambot4s.git</connection>
  </scm>
  <developers>
    <developer>
      <id>mukel</id>
      <name>Alfonso2 Peterssen</name>
      <url>http://mukel.info</url>
    </developer>
  </developers>
)
