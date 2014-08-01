name := "brix"

organization := ApplicationBuild.appIssuer

version := ApplicationBuild.appVersion

ApplicationBuild.defaultSettings

// Workaround to prevent SBT from producing an empty package
// for the root project
Keys.`package` := {
  (Keys.`package` in (`brix-corelib`, Compile)).value
}

lazy val `brix-corelib` = project.in(file("brix-corelib"))

lazy val brix = project.in(file(".")).settings(
  publishArtifact := false
).aggregate(
  `brix-corelib`
)
