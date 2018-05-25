scalaVersion := "2.12.4"
name := "mabeDemo"
organization := "com.arena"
version := "1.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "org.ddahl" %% "rscala" % "2.5.3"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-feature")
unmanagedJars in Compile += Attributed.blank(file("/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre/lib/ext/jfxrt.jar"))
fork := true

