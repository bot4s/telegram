import sbt._
import Keys._
import com.typesafe.sbt.SbtPgp.autoImport._
import com.typesafe.sbt.GitPlugin.autoImport._
import com.typesafe.sbt.{GitBranchPrompt, GitVersioning}
import sbtrelease._

object BuildSettings {
  val buildOrganization = "info.mukel"
  val buildVersion      = "2.1.0-SNAPSHOT"
  val buildScalaVersion = "2.12.1"
  val buildCrossScalaVersions = Seq("2.11.8", "2.12.1")

  val buildSettings = Seq (
    organization       := buildOrganization,
    version            := buildVersion,
    scalaVersion       := buildScalaVersion,
    crossScalaVersions := buildCrossScalaVersions,
    publishMavenStyle  := true,
    publishTo          := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
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
      </developers>)
  )
}

object Resolvers {
  val typesafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
}

object Dependencies {

  val akkaVersion         = "2.4.14"
  val akkaHttpVersion     = "10.0.0"
  val json4sVersion       = "3.5.0"
  val scalaTestVersion    = "3.0.0"
  val scalaCheckVersion   = "1.13.4"
  val scalaLoggingVersion = "3.5.0"
  val logbackVersion      = "1.1.7"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaHttp  = "com.typesafe.akka" %% "akka-http"  % akkaHttpVersion

  val json4s    = "org.json4s" %% "json4s-jackson" % json4sVersion
  val json4sExt = "org.json4s" %% "json4s-ext"     % json4sVersion

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

  val scalatest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"

  val akkaDependencies = Seq(akkaActor, akkaHttp)
  val miscDependencies = Seq(json4s, json4sExt, scalaLogging, logback)
  val testDependencies = Seq(scalatest, scalacheck)

  val allDependencies = akkaDependencies ++ miscDependencies ++ testDependencies
}

object TelegramBot4s extends Build {
  import Resolvers._
  import BuildSettings._
  import Defaults._

  lazy val telegramBot4s =
    Project ("telegrambot4s", file("."))
      .enablePlugins ( GitVersioning, GitBranchPrompt )
      .settings ( buildSettings : _* )
      .settings ( resolvers ++= Seq(typesafeRepo) )
      .settings ( libraryDependencies ++= Dependencies.allDependencies )
      .settings ( scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint", "-Xfatal-warnings", "-feature") )
}
