import mill._
import mill.scalalib._
import mill.scalalib.publish._

val ScalaVersions = Seq("2.12.20", "2.13.16")

object library {

  object Version {
    val circe              = "0.14.10"
    val circeGenericExtras = "0.14.4"
    val cats               = "2.13.0"
    val catsEffect         = "2.5.5"
    val zio                = "2.1.15"
    val catsEffect3        = "3.5.7"
    val zhttp              = "2.0.0-RC10"
    val zioInteropCats     = "23.1.0.3"
    val sttp               = "3.10.3"
    val scalaTest          = "3.2.19"
    val scalaMockScalaTest = "6.2.0"
    val scalaLogging       = "3.9.5"
    val logback            = "1.5.16"
    val scalajHttp         = "2.4.2"
    val akkaVersion        = "2.6.20"
    val akkaActor          = akkaVersion
    val akkaStream         = akkaVersion
    val akkaHttp           = "10.5.2"
    val akkaTestkit        = akkaVersion
    val akkaHttpCors       = "1.2.0"
    val hammock            = "0.11.3"
    val monix              = "3.4.1"
    val sbtTesting         = "1.0"
  }

  val akkaHttp        = ivy"com.typesafe.akka::akka-http::${Version.akkaHttp}"
  val akkaHttpTestkit = ivy"com.typesafe.akka::akka-http-testkit::${Version.akkaHttp}"
  val akkaTestkit     = ivy"com.typesafe.akka::akka-testkit::${Version.akkaTestkit}"
  val akkaActor       = ivy"com.typesafe.akka::akka-actor::${Version.akkaActor}"
  val akkaStream      = ivy"com.typesafe.akka::akka-stream::${Version.akkaStream}"
  val asyncHttpClientBackendCats =
    ivy"com.softwaremill.sttp.client3::async-http-client-backend-cats-ce2::${Version.sttp}"
  val asyncHttpClientBackendCats3 =
    ivy"com.softwaremill.sttp.client3::async-http-client-backend-cats::${Version.sttp}"
  val asyncHttpClientBackendZio =
    ivy"com.softwaremill.sttp.client3::async-http-client-backend-zio::${Version.sttp}"

  val asyncHttpClientBackendMonix = ivy"com.softwaremill.sttp.client3::async-http-client-backend-monix::${Version.sttp}"
  val scalajHttp                  = ivy"org.scalaj::scalaj-http::${Version.scalajHttp}"
  val scalaLogging                = ivy"com.typesafe.scala-logging::scala-logging::${Version.scalaLogging}"
  val scalaMockScalaTest          = ivy"org.scalamock::scalamock::${Version.scalaMockScalaTest}"
  val akkaHttpCors                = ivy"ch.megard::akka-http-cors::${Version.akkaHttpCors}"
  val scalaTest                   = ivy"org.scalatest::scalatest::${Version.scalaTest}"
  val sbtTesting                  = ivy"org.scala-sbt:test-interface:${Version.sbtTesting}"

  val logback            = ivy"ch.qos.logback:logback-classic::${Version.logback}"
  val circeCore          = ivy"io.circe::circe-core::${Version.circe}"
  val circeGeneric       = ivy"io.circe::circe-generic::${Version.circe}"
  val circeGenericExtras = ivy"io.circe::circe-generic-extras::${Version.circeGenericExtras}"
  val circeParser        = ivy"io.circe::circe-parser::${Version.circe}"
  val circeLiteral       = ivy"io.circe::circe-literal::${Version.circe}"
  val catsCore           = ivy"org.typelevel::cats-core::${Version.cats}"
  val catsFree           = ivy"org.typelevel::cats-free::${Version.cats}"
  val catsEffect         = ivy"org.typelevel::cats-effect::${Version.catsEffect}"
  val catsEffect3        = ivy"org.typelevel::cats-effect::${Version.catsEffect3}"
  val monix              = ivy"io.monix::monix::${Version.monix}"
  val sttpCore           = ivy"com.softwaremill.sttp.client3::core::${Version.sttp}"
  val sttpCirce          = ivy"com.softwaremill.sttp.client3::circe::${Version.sttp}"
  val sttpOkHttp         = ivy"com.softwaremill.sttp.client3::okhttp-backend::${Version.sttp}"
  val hammock            = ivy"com.pepegar::hammock-core::${Version.hammock}"
  val zio                = ivy"dev.zio::zio::${Version.zio}"
  val zhttp              = ivy"io.d11::zhttp::${Version.zhttp}"
  val zioInteropCats     = ivy"dev.zio::zio-interop-cats::${Version.zioInteropCats}"
}

trait Bot4sTelegramModule extends CrossScalaModule {

  override def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
    "-Xlint:_",
    // circe raises a lot of those warnings in the CirceEncoders file
    "-Wconf:cat=lint-byname-implicit:s",
    "-Ywarn-dead-code"
  )

  override def ivyDeps = T {
    Agg(
      library.circeCore,
      library.circeGeneric,
      library.circeGenericExtras,
      library.circeParser,
      library.circeLiteral,
      library.catsCore,
      library.catsFree,
      library.sttpCore,
      library.scalaLogging
    )
  }

  trait Tests extends ScalaTests with TestModule.ScalaTest {
    override def ivyDeps = T {
      super.ivyDeps() ++ Agg(
        library.scalaTest,
        library.scalaMockScalaTest,
        library.sbtTesting
      )
    }
  }

}

trait Bot4sTelegramCrossPlatform extends Bot4sTelegramModule {
  val platformSegment: String
  val location: String

  def millSourcePath = build.millSourcePath / location

  override def sources = T.sources(
    millSourcePath / "src",
    millSourcePath / s"src-$platformSegment"
  )

  def crossScalaVersion: String

  override def artifactName = s"telegram-$location"
}

trait Publishable extends PublishModule {

  override def publishVersion = "5.8.4"

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

object core extends Module {

  object jvm extends Cross[CoreJvmModule](ScalaVersions)
  trait CoreJvmModule extends Bot4sTelegramCrossPlatform with Publishable {
    override val platformSegment: String = "jvm"
    override val location: String        = "core"

    override def ivyDeps = T {
      super.ivyDeps() ++ Agg(
        library.scalajHttp
      )
    }

    object test extends Tests
  }

}

object akka extends Module {

  object jvm extends Cross[AkkaModule](ScalaVersions)
  trait AkkaModule extends Bot4sTelegramCrossPlatform with Publishable {
    override val platformSegment: String = "jvm"
    override val location: String        = "akka"
    override def artifactName            = "telegram-akka"

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.akkaActor,
      library.akkaHttp,
      library.akkaStream
    )

    override def moduleDeps = Seq(core.jvm())

    object test extends Tests {
      override def moduleDeps = super.moduleDeps ++ Seq(core.jvm().test)

      override def ivyDeps = T {
        super.ivyDeps() ++ Agg(
          library.akkaTestkit,
          library.akkaHttpTestkit
        )
      }
    }
  }
}

object examples extends Module {
  trait ExamplesJvmCommon extends Bot4sTelegramCrossPlatform {
    override val platformSegment: String = "jvm"

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.logback
    )
  }

  object jvm extends Cross[ExamplesJvmModule](ScalaVersions)
  trait ExamplesJvmModule extends ExamplesJvmCommon {
    override val location: String = "examples"

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.scalajHttp,
      library.akkaHttpCors,
      library.sttpOkHttp
    )

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm(), akka.jvm())
  }

  object catsjvm extends Cross[ExamplesCatsModule](ScalaVersions)
  trait ExamplesCatsModule extends ExamplesJvmCommon {
    override val location: String = "cats"

    override def ivyDeps = super.ivyDeps() ++ Agg(
      library.asyncHttpClientBackendCats,
      library.catsEffect
    )

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = T.sources(build.millSourcePath / "examples" / "src-cats")
  }

  object cats3jvm extends Cross[ExamplesCats3Module](ScalaVersions)
  trait ExamplesCats3Module extends ExamplesJvmCommon {
    override val location: String = "cats"

    override def ivyDeps = T {
      super.ivyDeps() ++ Agg(
        library.asyncHttpClientBackendCats3,
        library.catsEffect3
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = T.sources(build.millSourcePath / "examples" / "src-cats3")
  }

  object ziojvm extends Cross[ExamplesZIOModule](ScalaVersions)
  trait ExamplesZIOModule extends ExamplesJvmCommon {
    override val location: String = "zio"

    override def ivyDeps = T {
      super.ivyDeps() ++ Agg(
        library.asyncHttpClientBackendZio,
        library.zio,
        library.zhttp,
        library.zioInteropCats,
        library.catsEffect3
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = T.sources(build.millSourcePath / "examples" / "src-zio")
  }

  object monixjvm extends Cross[ExamplesMonixModule](ScalaVersions)
  trait ExamplesMonixModule extends ExamplesJvmCommon {
    override val location: String = "monix"

    override def ivyDeps = T {
      super.ivyDeps() ++ Agg(
        library.asyncHttpClientBackendMonix,
        library.monix
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = T.sources(build.millSourcePath / "examples" / "src-monix")
  }
}
