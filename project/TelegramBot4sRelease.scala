import java.util.regex.Pattern

import sbt._
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys._
import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, _}
import sbtrelease.ReleaseStateTransformations._

object TelegramBot4sRelease {

  def steps: Seq[ReleaseStep] = Seq(
    checkSnapshotDependencies,
    inquireVersions,
    // publishing locally so that the pgp password prompt is displayed early
    // in the process
    releaseStepCommand("publishLocalSigned"),
    runClean,
    runTest,
    setReleaseVersion,
    updateVersionInReadme,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )

  private def updateVersionInReadme: ReleaseStep = { s: State =>
    val readmeFile = file("README.md")
    val readme = IO.read(readmeFile)

    val currentVersionPattern = """"info.mukel" %% "telegrambot4s" % "([\w\.-]+)"""".r
    val currentVersionInReadme = currentVersionPattern.findFirstMatchIn(readme).get.group(1)

    val releaseVersion = s.get(versions).get._1

    s.log.info(s"Replacing $currentVersionInReadme with $releaseVersion in ${readmeFile.name}")

    val newReadme = readme.replaceAll(Pattern.quote(currentVersionInReadme), releaseVersion)
    IO.write(readmeFile, newReadme)

    val settings = Project.extract(s)
    settings.get(releaseVcs).get.add(readmeFile.getAbsolutePath) !! s.log

    s
  }
} 
