name := "brix-corelib"

organization := ApplicationBuild.appIssuer

version := ApplicationBuild.appVersion

ApplicationBuild.defaultSettings

libraryDependencies ++= Seq(
  "commons-codec" % "commons-codec" % "1.8"
)
