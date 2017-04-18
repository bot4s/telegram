name := "telegrambot4s"
organization       := "info.mukel"
version            := "2.2.1-SNAPSHOT"

scalaVersion       := "2.12.1"
crossScalaVersions := Seq("2.11.8", "2.12.1")

licenses := Seq("Apache 2.0 License" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
homepage := Some(url("https://github.com/mukel/telegrambot4s"))

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint:_",
  "-Yno-adapted-args",
  "-Ywarn-dead-code"
)

resolvers += Resolver.typesafeRepo("releases")

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-actor"      % "2.4.17",
  "com.typesafe.akka"          %% "akka-http"       % "10.0.5",
  "org.json4s"                 %% "json4s-jackson"  % "3.5.0",
  "org.json4s"                 %% "json4s-ext"      % "3.5.0",
  "com.typesafe.scala-logging" %% "scala-logging"   % "3.5.0",
  "ch.qos.logback"             %  "logback-classic" % "1.1.7" % "test"
)

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishMavenStyle := true

publishTo          := {
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