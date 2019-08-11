import org.scalajs.jsenv.nodejs.NodeJSEnv

lazy val baseSettings: Project => Project =
  _.enablePlugins(ScalaJSPlugin)
    .settings(
      scalaVersion := "2.12.8",
      version := "0.1-SNAPSHOT",
      scalacOptions ++= ScalacOptions.flags,
      /* in preparation for scala.js 1.0 */
      scalacOptions += "-P:scalajs:sjsDefinedByDefault",
      /* for ScalablyTyped */
      resolvers += Resolver.bintrayRepo("oyvindberg", "ScalablyTyped"),
    )

lazy val application: Project => Project =
  _.settings(
    scalaJSUseMainModuleInitializer := true,
    /* disabled because it somehow triggers many warnings */
    emitSourceMaps := false,
    scalaJSModuleKind := ModuleKind.CommonJSModule,
  )

lazy val electron = project
  .configure(baseSettings, outputModule, application)
  .settings(
    libraryDependencies ++= Seq(ScalablyTyped.E.electron),
    /* run with globally installed electron */
    jsEnv := new NodeJSEnv(
      NodeJSEnv
        .Config()
        .withExecutable("electron")
        .withArgs(List((Compile / classDirectory).value.toString))
    ),
  )

val outputModule: Project => Project =
  _.settings(scalaJSModuleKind := ModuleKind.CommonJSModule)
