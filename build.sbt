name := "ScalaProject"

lazy val commonSettings = Seq(
  scalaVersion := "2.11.8",
  version := "0.1"
)

lazy val core = project
  .settings(commonSettings)
  .settings(Seq(
    libraryDependencies ++= Seq(
      "org.tensorflow" % "tensorflow" % "1.2.1",
      "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly()
    )
  ))

//lazy val rest = project
//  .enablePlugins(PlayScala)
//  //.dependsOn(core)
//  .settings(commonSettings)
//  .settings(Seq(
//    resolvers += Resolver.sonatypeRepo("snapshots"),
//    libraryDependencies ++= Seq(
//      guice,
//      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
//      "com.h2database" % "h2" % "1.4.196"
//      //"com.twitter" %% "finatra-http" % "2.11.0",
//
//    ),
//    excludeDependencies += "org.slf4j" % "slf4j-log4j12"
//  ))

lazy val root = (project in file("."))
  .aggregate(core)
  .dependsOn(core)
  .settings(commonSettings)

