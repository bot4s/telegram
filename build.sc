import mill._
import mill.scalalib._
import mill.scalalib.publish._
import mill.javalib.api.JvmWorkerUtil

val ScalaVersions = Seq("2.12.20", "2.13.16", "3.3.6")

object library {

  object Version {
    val circe              = "0.14.14"
    val circeGenericExtras = "0.14.5-RC1"
    val cats               = "2.13.0"
    val catsEffect         = "2.5.5"
    val zio                = "2.1.19"
    val catsEffect3        = "3.6.2"
    val zhttp              = "2.0.0-RC10"
    val zioInteropCats     = "23.1.0.5"
    val sttp               = "3.11.0"
    val scalaTest          = "3.2.19"
    val scalaMockScalaTest = "7.4.0"
    val scalaLogging       = "3.9.5"
    val logback            = "1.5.18"
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

  val akkaHttp        = mvn"com.typesafe.akka::akka-http::${Version.akkaHttp}"
  val akkaHttpTestkit = mvn"com.typesafe.akka::akka-http-testkit::${Version.akkaHttp}"
  val akkaTestkit     = mvn"com.typesafe.akka::akka-testkit::${Version.akkaTestkit}"
  val akkaActor       = mvn"com.typesafe.akka::akka-actor::${Version.akkaActor}"
  val akkaStream      = mvn"com.typesafe.akka::akka-stream::${Version.akkaStream}"
  val asyncHttpClientBackendCats =
    mvn"com.softwaremill.sttp.client3::async-http-client-backend-cats-ce2::${Version.sttp}"
  val asyncHttpClientBackendCats3 =
    mvn"com.softwaremill.sttp.client3::async-http-client-backend-cats::${Version.sttp}"
  val asyncHttpClientBackendZio =
    mvn"com.softwaremill.sttp.client3::async-http-client-backend-zio::${Version.sttp}"

  val asyncHttpClientBackendMonix = mvn"com.softwaremill.sttp.client3::async-http-client-backend-monix::${Version.sttp}"
  val scalajHttp                  = mvn"org.scalaj::scalaj-http::${Version.scalajHttp}"
  val scalaLogging                = mvn"com.typesafe.scala-logging::scala-logging::${Version.scalaLogging}"
  val scalaMockScalaTest          = mvn"org.scalamock::scalamock::${Version.scalaMockScalaTest}"
  val akkaHttpCors                = mvn"ch.megard::akka-http-cors::${Version.akkaHttpCors}"
  val scalaTest                   = mvn"org.scalatest::scalatest::${Version.scalaTest}"
  val sbtTesting                  = mvn"org.scala-sbt:test-interface:${Version.sbtTesting}"

  val logback            = mvn"ch.qos.logback:logback-classic::${Version.logback}"
  val circeCore          = mvn"io.circe::circe-core::${Version.circe}"
  val circeGeneric       = mvn"io.circe::circe-generic::${Version.circe}"
  val circeGenericExtras = mvn"io.circe::circe-generic-extras::${Version.circeGenericExtras}"
  val circeParser        = mvn"io.circe::circe-parser::${Version.circe}"
  val circeLiteral       = mvn"io.circe::circe-literal::${Version.circe}"
  val catsCore           = mvn"org.typelevel::cats-core::${Version.cats}"
  val catsFree           = mvn"org.typelevel::cats-free::${Version.cats}"
  val catsEffect         = mvn"org.typelevel::cats-effect::${Version.catsEffect}"
  val catsEffect3        = mvn"org.typelevel::cats-effect::${Version.catsEffect3}"
  val monix              = mvn"io.monix::monix::${Version.monix}"
  val sttpCore           = mvn"com.softwaremill.sttp.client3::core::${Version.sttp}"
  val sttpCirce          = mvn"com.softwaremill.sttp.client3::circe::${Version.sttp}"
  val sttpOkHttp         = mvn"com.softwaremill.sttp.client3::okhttp-backend::${Version.sttp}"
  val hammock            = mvn"com.pepegar::hammock-core::${Version.hammock}"
  val zio                = mvn"dev.zio::zio::${Version.zio}"
  val zhttp              = mvn"io.d11::zhttp::${Version.zhttp}"
  val zioInteropCats     = mvn"dev.zio::zio-interop-cats::${Version.zioInteropCats}"
}

trait Bot4sTelegramModule extends CrossScalaModule {
  protected def isScala3: Task[Boolean] = Task(JvmWorkerUtil.isScala3(scalaVersion()))

  override def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:implicitConversions",
    "-language:higherKinds"
  )

  override def mvnDeps = Seq(
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

  trait Tests extends ScalaTests with TestModule.ScalaTest {
    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
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

  def moduleDir = build.moduleDir / location

  def versionSources = Task.Sources(scalaVersionDirectoryNames.map(s => moduleDir / s"src-$platformSegment-$s") *)

  def regularSources = Task.Sources(
    moduleDir / "src",
    moduleDir / s"src-$platformSegment"
  )
  override def sources = Task {
    versionSources() ++ regularSources()
  }

  override def artifactName = s"telegram-$location"
}

trait Publishable extends SonatypeCentralPublishModule {

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

    def versionDependencies = Task {
      if (isScala3()) Seq.empty else Seq(library.scalajHttp)
    }

    override def mvnDeps = Task {
      super.mvnDeps() ++ versionDependencies()
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

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.akkaActor,
        library.akkaHttp,
        library.akkaStream
      )
    }

    override def moduleDeps = Seq(core.jvm())

    object test extends Tests {
      override def moduleDeps = super.moduleDeps ++ Seq(core.jvm().test)

      override def mvnDeps = Task {
        super.mvnDeps() ++ Seq(
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

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(library.logback)
    }
  }

  object jvm extends Cross[ExamplesJvmModule](ScalaVersions)
  trait ExamplesJvmModule extends ExamplesJvmCommon {
    override val location: String = "examples"

    def versionDependencies = Task {
      if (isScala3()) Seq.empty else Seq(library.scalajHttp)
    }

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.akkaHttpCors,
        library.sttpOkHttp
      ) ++ versionDependencies()
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm(), akka.jvm())
  }

  object catsjvm extends Cross[ExamplesCatsModule](ScalaVersions)
  trait ExamplesCatsModule extends ExamplesJvmCommon {
    override val location: String = "cats"

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.asyncHttpClientBackendCats,
        library.catsEffect
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = Task.Sources(build.moduleDir / "examples" / "src-cats")
  }

  object cats3jvm extends Cross[ExamplesCats3Module](ScalaVersions)
  trait ExamplesCats3Module extends ExamplesJvmCommon {
    override val location: String = "cats"

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.asyncHttpClientBackendCats3,
        library.catsEffect3
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = Task.Sources(build.moduleDir / "examples" / "src-cats3")
  }

  object ziojvm extends Cross[ExamplesZIOModule](ScalaVersions)
  trait ExamplesZIOModule extends ExamplesJvmCommon {
    override val location: String = "zio"

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.asyncHttpClientBackendZio,
        library.zio,
        library.zhttp,
        library.zioInteropCats,
        library.catsEffect3
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = Task.Sources(build.moduleDir / "examples" / "src-zio")
  }

  object monixjvm extends Cross[ExamplesMonixModule](ScalaVersions)
  trait ExamplesMonixModule extends ExamplesJvmCommon {
    override val location: String = "monix"

    override def mvnDeps = Task {
      super.mvnDeps() ++ Seq(
        library.asyncHttpClientBackendMonix,
        library.monix
      )
    }

    override def moduleDeps = super.moduleDeps ++ Seq(core.jvm())

    override def sources = Task.Sources(build.moduleDir / "examples" / "src-monix")
  }
}
