import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "GestionCourrier"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,

    "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.2.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(

  )
}
