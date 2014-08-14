import sbt._
import Keys._

object ApplicationBuild extends Build {

  val appIssuer = "com.gokillo"

  /*
  val branch = "git rev-parse --abbrev-ref HEAD".!!.trim
  val commit = "git rev-parse --short HEAD".!!.trim
  val buildTime = (new java.text.SimpleDateFormat("yyyyMMdd-HHmmss")).format(new java.util.Date())
  val appVersion = "%s-%s-%s".format(branch, commit, buildTime)
  */
  val appVersion = "0.1.0-SNAPSHOT"

  val defaultScalacOptions = Seq(
    "-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls", "-language:implicitConversions",
    "-language:postfixOps", "-language:dynamics", "-language:higherKinds", "-language:existentials",
    "-language:experimental.macros", "-encoding", "UTF-8", "-Xmax-classfile-name", "140")

  val defaultResolvers = Seq(
    "Typesafe releases repository" at "http://repo.typesafe.com/typesafe/releases/"
  )

  val defaultLibraryDependencies = Seq(
    "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3-1",
    "org.specs2" %% "specs2" % "2.4" % "test",
    "org.slf4j" % "slf4j-nop" % "1.7.7" % "test"
  )

  val defaultSettings = Defaults.coreDefaultSettings ++ Seq(
    scalaVersion := "2.11.1",
    scalacOptions ++= defaultScalacOptions,
    resolvers ++= defaultResolvers,
    libraryDependencies ++= defaultLibraryDependencies,
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    publishTo <<= (version) { v =>
      val host = "ftp.gokillo.com"
      val port = 8999
      val target = if (v.trim.endsWith("SNAPSHOT")) "snapshots" else "releases"
      val name = new scala.collection.immutable.StringOps(target)
      Some(Resolver.sftp(name, host, port, target)(Resolver.ivyStylePatterns))
    },
    startYear := Some(2014),
    description := "Sugar Cubes for Scala Development",
    licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url("http://dev.gokillo.com")),
    scmInfo := Some(ScmInfo(url("https://github.com/gokillo/brix"), "https://github.com/gokillo/brix.git"))
  )
}
