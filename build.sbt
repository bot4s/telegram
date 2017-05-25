name               := "telegrambot4s"
organization       := "info.mukel"
scalaVersion       := "2.12.2"
crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  //"-Xfatal-warnings",
  "-Xlint:_",
  "-Yno-adapted-args",
  "-Ywarn-dead-code"
)

resolvers += Resolver.typesafeRepo("releases")

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-actor"      % "2.4.18",
  "com.typesafe.akka"          %% "akka-http"       % "10.0.5",
  "org.json4s"                 %% "json4s-jackson"  % "3.5.0",
  "org.json4s"                 %% "json4s-ext"      % "3.5.0",
  "com.typesafe.scala-logging" %% "scala-logging"   % "3.5.0",
  "ch.qos.logback"             %  "logback-classic" % "1.1.7"   % "test",
  "org.scalatest"              %% "scalatest"       % "3.0.1"   % "test",
  "org.scalamock"              %% "scalamock-scalatest-support" % "3.5.0" % Test
)

fork := true
cancelable in Global := true
parallelExecution in Test := false

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

lazy val updateVersionInReadme = ReleaseStep(action = st => {
  val extracted = Project.extract(st)
  val projectVersion = extracted.get(Keys.version)

  println(s"Updating project version to $projectVersion in the README")
  Process(Seq("sed", "-i", "", "-E", "-e", s"""s/"info.mukel" %% "telegrambot4s" % ".*"/"info.mukel" %% "telegrambot4s" % "$projectVersion"/g""", "README.md")).!

  println("Committing README.md")
  Process(Seq("git", "commit", "README.md", "-m", s"Update project version in README to $projectVersion")).!

  st
})

commands += Command.command("update-version-in-readme")(updateVersionInReadme)

import sbtrelease.ReleaseStateTransformations._

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  updateVersionInReadme,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  pushChanges
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

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
        <name>Alfonso² Peterssen</name>
        <url>http://mukel.info</url>
      </developer>
    </developers>
)
