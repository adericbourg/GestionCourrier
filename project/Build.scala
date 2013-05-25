import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "GestionCourrier"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,

    "org.webjars" % "webjars-play" % "2.1.0-1",
    "org.webjars" % "html5shiv" % "3.6.2",

    // UI
    "org.webjars" % "angularjs" % "1.1.5",
    "org.webjars" % "angular-ui" % "0.4.0-1",
    "org.webjars" % "angular-ui-bootstrap" % "0.3.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(

  )

}
