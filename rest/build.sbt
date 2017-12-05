/*
lazy val core = project in file("../core")

lazy val rest = (project in file(".")).enablePlugins(PlayScala).dependsOn(core)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"*/
