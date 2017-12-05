// The Play plugin
resolvers += Resolver.typesafeRepo("snapshots")

resolvers += Resolver.jcenterRepo
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.7")
