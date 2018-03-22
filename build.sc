import mill._
import mill.scalalib._
import mill.scalalib.publish._

val crossScalaVersions = Seq("2.11.11", "2.12.4")

object library {

  object Version {
    val akkaVersion = "2.5.11"
    val akkaActor = akkaVersion
    val akkaStream = akkaVersion
    val akkaHttp = "10.1.0"
    val json4s = "3.5.3"
    val scalaTest = "3.0.5"
    val scalajHttp = "2.3.0"
    val scalamock = "3.6.0"
    val scalaLogging = "3.8.0"
    val logback = "1.2.3"
    val akkaHttpCors = "0.2.2"
  }

  val akkaHttp = ivy"com.typesafe.akka::akka-http:${Version.akkaHttp}"
  val akkaHttpTestkit = ivy"com.typesafe.akka::akka-http-testkit:${Version.akkaHttp}"
  val akkaActor = ivy"com.typesafe.akka::akka-actor:${Version.akkaActor}"
  val akkaStream = ivy"com.typesafe.akka::akka-stream:${Version.akkaStream}"
  val json4sCore = ivy"org.json4s::json4s-core:${Version.json4s}"
  val json4sJackson = ivy"org.json4s::json4s-jackson:${Version.json4s}"
  val json4sNative = ivy"org.json4s::json4s-native:${Version.json4s}"
  val json4sExt = ivy"org.json4s::json4s-ext:${Version.json4s}"
  val scalajHttp = ivy"org.scalaj::scalaj-http:${Version.scalajHttp}"
  val scalaLogging = ivy"com.typesafe.scala-logging::scala-logging:${Version.scalaLogging}"
  val scalaMock = ivy"org.scalamock::scalamock-scalatest-support:${Version.scalamock}"
  val akkaHttpCors = ivy"ch.megard::akka-http-cors:${Version.akkaHttpCors}"
  val scalaTest = ivy"org.scalatest::scalatest:${Version.scalaTest}"
  val logback = ivy"ch.qos.logback:logback-classic:${Version.logback}"
}

trait TelegramBot4sModule extends CrossScalaModule {

  override def scalacOptions = Seq(
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
  )

  trait Tests extends super.Tests {
    override def ivyDeps = Agg(
      library.scalaTest,
      library.scalaMock,
      library.logback,
      library.scalaLogging
    )

    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

}

trait Publishable extends PublishModule {

  override def publishVersion = "3.1.0-RC1"

  def pomSettings = PomSettings(
    description = "Telegram Bot API wrapper for Scala",
    organization = "info.mukel",
    url = "https://github.com/mukel/telegrambot4s",
    licenses = Seq(License.Common.Apache2),
    versionControl = VersionControl.github("mukel", "telegrambot4s"),
    developers = Seq(
      Developer("mukel", "Alfonso² Peterssen", "https://github.com/mukel")
    )
  )
}

object core extends Cross[CoreModule](crossScalaVersions: _ *)

class CoreModule(val crossScalaVersion: String) extends TelegramBot4sModule with Publishable {
  override def artifactName = "telegrambot4s-core"

  override def ivyDeps = Agg(
    library.json4sCore,
    library.json4sJackson,
    library.json4sExt,
    library.scalaLogging,
    library.scalajHttp
  )

  object test extends Tests

}

object akka extends Cross[AkkaModule](crossScalaVersions: _ *)

class AkkaModule(val crossScalaVersion: String) extends TelegramBot4sModule with Publishable {
  override def artifactName = "telegrambot4s-akka"

  override def moduleDeps = Seq(core())

  override def ivyDeps = Agg(
    library.akkaActor,
    library.akkaHttp,
    library.akkaStream
  )

  object test extends Tests {
    override def moduleDeps = super.moduleDeps ++ Seq(core().test)

    override def ivyDeps = Agg(
      library.akkaHttpTestkit
    )
  }

}

object examples extends Cross[ExamplesModule](crossScalaVersions: _ *)

class ExamplesModule(val crossScalaVersion: String) extends TelegramBot4sModule {
  override def moduleDeps = Seq(core(), akka())

  override def ivyDeps = Agg(
    library.akkaHttpCors,
    library.akkaHttpCors,
    library.logback
  )
}
