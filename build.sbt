name := "ScalaProject"
val scalaTestVersion = "3.0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.11.8"
//  version := "0.0.3-SNAPSHOT"
)

lazy val core = project
  .settings(commonSettings)
  .settings(Seq(
    libraryDependencies ++= Seq(
      "org.tensorflow" % "tensorflow" % "1.2.1",
      "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly(),
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "org.apache.spark" % "spark-core_2.11" % "2.2.0",
      "org.apache.spark" % "spark-sql_2.11" % "2.2.0"
    )
  ))

lazy val rest = project
  .enablePlugins(PlayScala)
  .dependsOn(core)
  .settings(commonSettings)
  .settings(Seq(
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "com.h2database" % "h2" % "1.4.196",
      ws,
      guice
      //"com.twitter" %% "finatra-http" % "2.11.0",

    ),
    excludeDependencies += "org.slf4j" % "slf4j-log4j12"
  ))

lazy val root = (project in file("."))
  .aggregate(core, rest)
  .dependsOn(core, rest)
  .settings(commonSettings)

parallelExecution in Test := false