// *****************************************************************************
// Projects
// *****************************************************************************

lazy val rootProject: Project =
  project
    .in(file("."))
    .settings(commonSettings)
    .settings(doNotPublishSettings)
    .settings(name := "telegrambot4s")
    .aggregate(
      core,
      examples,
      tests
    )

lazy val core: Project =
  project
    .in(file("core"))
    .settings(commonSettings)
    .settings(publishSettings)
    .settings(
      name := "telegrambot4s",
      libraryDependencies ++= Seq(
        library.akkaActor,
        library.akkaHttp,
        library.akkaStream,
        library.json4sCore,
        library.json4sJackson,
        library.json4sExt,
        library.scalaLogging
      )
    )

lazy val tests: Project =
  project
    .in(file("tests"))
    .settings(commonSettings)
    .settings(doNotPublishSettings)
    .settings(
      name := "tests",
      libraryDependencies ++= Seq(
        library.scalaTest,
        library.scalaMock,
        library.akkaHttp,
        library.akkaHttpTestkit,
        library.logback,
        library.scalaLogging
      ).map(_ % Test)
    ).dependsOn(
      core
    )

lazy val examples: Project =
  project
    .in(file("examples"))
    .settings(commonSettings)
    .settings(doNotPublishSettings)
    .settings(
      fork := true,
      cancelable in Global := true
    )
    .settings(
      name := "examples",
      libraryDependencies ++= Seq(
        library.akkaHttp,
        library.akkaStream,
        library.akkaHttpCors,
        library.logback,
        library.scalaLogging
      )
    ).dependsOn(
      core
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

  lazy val library =
  new {
    object Version {
      val akkaVersion  = "2.4.19"
      val akkaActor    = akkaVersion
      val akkaStream   = akkaVersion
      val akkaHttp     = "10.0.10"
      val json4s       = "3.5.3"
      val scalaTest    = "3.0.3"
      val scalajHttp   = "2.3.0"
      val scalamock    = "3.6.0"
      val scalaLogging = "3.7.2"
      val logback      = "1.2.3"
      val akkaHttpCors = "0.2.1"
    }

    val akkaHttp        = "com.typesafe.akka"           %% "akka-http"            % Version.akkaHttp
    val akkaHttpTestkit = "com.typesafe.akka"           %% "akka-http-testkit"    % Version.akkaHttp

    val akkaActor       = "com.typesafe.akka"           %% "akka-actor"           % Version.akkaActor
    val akkaStream      = "com.typesafe.akka"           %% "akka-stream"          % Version.akkaStream
    val json4sCore      = "org.json4s"                  %% "json4s-core"          % Version.json4s
    val json4sJackson   = "org.json4s"                  %% "json4s-jackson"       % Version.json4s
    val json4sNative    = "org.json4s"                  %% "json4s-native"        % Version.json4s
    val json4sExt       = "org.json4s"                  %% "json4s-ext"           % Version.json4s
    val scalajHttp      = "org.scalaj"                  %% "scalaj-http"          % Version.scalajHttp
    val scalaLogging    = "com.typesafe.scala-logging"  %% "scala-logging"        % Version.scalaLogging
    val scalaMock       = "org.scalamock"               %% "scalamock-scalatest-support" % Version.scalamock
    val akkaHttpCors    = "ch.megard"                   %% "akka-http-cors"       % Version.akkaHttpCors
    val scalaTest       = "org.scalatest"               %% "scalatest"            % Version.scalaTest
    val logback         = "ch.qos.logback"              %  "logback-classic"      % Version.logback
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val commonSettings =
  Seq(
    organization := "info.mukel",
    organizationName := "Alfonso² Peterssen",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
    startYear := Some(2015),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      //"-Xfatal-warnings",
      "-Xlint:_",
      "-Yno-adapted-args",
      "-Ywarn-dead-code"
    ),
    shellPrompt in ThisBuild := { state =>
      val project = Project.extract(state).currentRef.project
      s"[$project]> "
    }
  )

lazy val publishSettings =
  Seq(
    homepage := Some(url("https://github.com/mukel/telegrambot4s")),
    scmInfo := Some(ScmInfo(url("https://github.com/mukel/telegrambot4s"),
      "git@github.com:mukel/telegrambot4s.git")),
    developers += Developer("mukel",
      "Alfonso² Peterssen",
      "mukel@users.noreply.github.com",
      url("https://github.com/mukel")),

    pomIncludeRepository := (_ => false),
    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),
    publishArtifact in Test := false,
    publishMavenStyle := true,

    // sbt-release
    releaseCrossBuild := true,
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseIgnoreUntrackedFiles := true,
    releaseProcess := TelegramBot4sRelease.steps
  )

lazy val doNotPublishSettings =
  Seq(
    publishArtifact := false
  )