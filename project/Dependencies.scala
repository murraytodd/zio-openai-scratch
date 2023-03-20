import sbt.*

object Dependencies {
  lazy val ziolibs = Seq(
    "dev.zio" %% "zio" % "2.0.10",
    "dev.zio" %% "zio-config" % "4.0.0-RC12",
    "dev.zio" %% "zio-openai" % "0.2.0"
  )
}
