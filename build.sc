import mill._
import mill.scalalib._
import mill.scalalib.publish._
import ammonite.ops._

val ScalaVersions = Seq("2.12.13", "2.13.4")

object library {

  object Version {
    val circe              = "0.13.0"
    val cats               = "2.1.1"
    val catsEffect         = "2.3.1"
    val rosHttp            = "3.0.0" // Note: This is no longer maintained
    val sttp               = "1.7.2" // TODO: migrate to 2.x
    val scalaTest          = "3.2.2"
    val scalaMockScalaTest = "5.1.0"
    val scalaLogging       = "3.9.2"
    val logback            = "1.2.3"
    val scalajHttp         = "2.4.2"
    val akkaVersion        = "2.6.13"
    val akkaActor          = akkaVersion
    val akkaStream         = akkaVersion
    val akkaHttp           = "10.2.4"
    val akkaTestkit        = akkaVersion
    val akkaHttpCors       = "1.1.1"
    val hammock            = "0.11.3"
    val monix              = "3.3.0"
  }

  val akkaHttp           = ivy"com.typesafe.akka::akka-http::${Version.akkaHttp}"
  val akkaHttpTestkit    = ivy"com.typesafe.akka::akka-http-testkit::${Version.akkaHttp}"
  val akkaTestkit        = ivy"com.typesafe.akka::akka-testkit::${Version.akkaTestkit}"
  val akkaActor          = ivy"com.typesafe.akka::akka-actor::${Version.akkaActor}"
  val akkaStream         = ivy"com.typesafe.akka::akka-stream::${Version.akkaStream}"
  val asyncHttpClientBackendCats = ivy"com.softwaremill.sttp::async-http-client-backend-cats::${Version.sttp}"
  val asyncHttpClientBackendMonix = ivy"com.softwaremill.sttp::async-http-client-backend-monix::${Version.sttp}"
  val scalajHttp         = ivy"org.scalaj::scalaj-http::${Version.scalajHttp}"
  val scalaLogging       = ivy"com.typesafe.scala-logging::scala-logging::${Version.scalaLogging}"
  val scalaMockScalaTest = ivy"org.scalamock::scalamock::${Version.scalaMockScalaTest}"
  val akkaHttpCors       = ivy"ch.megard::akka-http-cors::${Version.akkaHttpCors}"
  val scalaTest          = ivy"org.scalatest::scalatest::${Version.scalaTest}"
  val logback            = ivy"ch.qos.logback:logback-classic::${Version.logback}"
  val circeCore          = ivy"io.circe::circe-core::${Version.circe}"
  val circeGeneric       = ivy"io.circe::circe-generic::${Version.circe}"
  val circeGenericExtras = ivy"io.circe::circe-generic-extras::${Version.circe}"
  val circeParser        = ivy"io.circe::circe-parser::${Version.circe}"
  val circeLiteral       = ivy"io.circe::circe-literal::${Version.circe}"
  val catsCore           = ivy"org.typelevel::cats-core::${Version.cats}"
  val catsFree           = ivy"org.typelevel::cats-free::${Version.cats}"
  val catsEffect         = ivy"org.typelevel::cats-effect::${Version.catsEffect}"
  val monix              = ivy"io.monix::monix::${Version.monix}"
  val rosHttp            = ivy"fr.hmil::roshttp::${Version.rosHttp}"
  val sttpCore           = ivy"com.softwaremill.sttp::core::${Version.sttp}"
  val sttpCirce          = ivy"com.softwaremill.sttp::circe::${Version.sttp}"
  val sttpOkHttp         = ivy"com.softwaremill.sttp::okhttp-backend::${Version.sttp}"
  val hammock            = ivy"com.pepegar::hammock-core::${Version.hammock}"
}


trait Bot4sTelegramModule extends CrossScalaModule {

  override def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    //"-Xfatal-warnings",
    "-Xlint:_",
    "-Ywarn-dead-code"
  )

  override def ivyDeps = Agg(
    library.circeCore,
    library.circeGeneric,
    library.circeGenericExtras,
    library.circeParser,
    library.circeLiteral,
    library.catsCore,
    library.catsFree,
    library.sttpCore,
    library.scalaLogging,
    library.logback
  )

  trait Tests extends super.Tests {
    override def ivyDeps = Agg(
      library.scalaTest,
      library.scalaMockScalaTest
    )

    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

}

abstract class Bot4sTelegramCrossPlatform(val platformSegment: String, location: String) extends Bot4sTelegramModule {

  def millSourcePath = build.millSourcePath / location

  override def sources = T.sources(
    millSourcePath / "src",
    millSourcePath / s"src-$platformSegment"
  )

  def crossScalaVersion: String

  override def artifactName = s"telegram-$location"
}

trait Publishable extends PublishModule {

  override def publishVersion = "4.4.0-RC2"

  def pomSettings = PomSettings(
    description = "Telegram Bot API wrapper for Scala",
    organization = "com.bot4s",
    url = "https://github.com/bot4s/telegram",
    licenses = Seq(License.Common.Apache2),
    versionControl = VersionControl.github("bot4s", "telegram"),
    developers = Seq(
      Developer("mukel", "Alfonso² Peterssen", "https://github.com/mukel")
    )
  )
}

abstract class Bot4sTelegramCore(platformSegment: String) extends Bot4sTelegramCrossPlatform(platformSegment, "core")

object core extends Module {

  object jvm extends Cross[CoreJvmModule](ScalaVersions: _ *)

  class CoreJvmModule(val crossScalaVersion: String) extends Bot4sTelegramCore("jvm") with Publishable {
    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.scalajHttp,
      library.sttpOkHttp
    )

    object test extends Tests

  }

}

abstract class Bot4sTelegramAkka extends Bot4sTelegramCrossPlatform("jvm", "akka")

object akka extends Module {

  object jvm extends Cross[AkkaModule](ScalaVersions: _ *)

  class AkkaModule(val crossScalaVersion: String) extends Bot4sTelegramAkka with Publishable {
    override def artifactName = "telegram-akka"

    override def moduleDeps = Seq(core.jvm())

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.akkaActor,
      library.akkaHttp,
      library.akkaStream
    )

    object test extends Tests {
      override def moduleDeps = super.moduleDeps ++ Seq(core.jvm().test)

      override def ivyDeps = super.ivyDeps() ++ Agg(
        library.akkaTestkit,
        library.akkaHttpTestkit
      )
    }
  }
}

abstract class Bot4sTelegramExamples(platformSegment: String) extends Bot4sTelegramCrossPlatform(platformSegment, "examples")

object examples extends Module {

  object jvm extends Cross[ExamplesJvmModule](ScalaVersions: _ *)

  class ExamplesJvmModule(val crossScalaVersion: String) extends Bot4sTelegramExamples("jvm") {
    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm(), akka.jvm())

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.scalajHttp,
      library.akkaHttpCors,
      library.sttpOkHttp
    )
  }



  object catsjvm extends Cross[ExamplesCatsModule](ScalaVersions: _*)

  class ExamplesCatsModule(val crossScalaVersion: String) extends Bot4sTelegramExamples("cats") {
    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.asyncHttpClientBackendCats,
      library.catsEffect
    )

    override def sources = T.sources { build.millSourcePath / "examples" / "src-cats" }
  }

  object monixjvm extends Cross[ExamplesMonixModule](ScalaVersions: _*)

  class ExamplesMonixModule(val crossScalaVersion: String) extends Bot4sTelegramExamples("monix") {
    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.asyncHttpClientBackendMonix,
      library.monix
    )

    override def sources = T.sources { build.millSourcePath / "examples" / "src-monix" }
  }
}
