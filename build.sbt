import Dependencies._

val commonSettings = Seq(
  // NB: Modified based off of @tpolecat's list
  // https://tpolecat.github.io/2017/04/25/scalac-flags.html
  scalacOptions ++= Seq(
//    "-Xlog-implicits",

    "-target:jvm-1.8",
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-language:postfixOps",              // Allow postfix operator syntax
    //"-language:reflectiveCalls",         // Allow reflection
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    //     TODO 2.12.X
    //    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    //     TODO 2.12.X
    //     "-Ywarn-extra-implicit",            // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit"                // Warn when nullary methods return Unit.
    //     TODO 2.12.X
    //     "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    //     "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    //     "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    //     "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    //     "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    //     "-Ywarn-unused:privates",            // Warn if a private member is unused.
    //     "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
  )
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val ScalaVersion = "2.12.8"
val ProjectVersion = "0.1.0-SNAPSHOT"

lazy val gentables = (project in file("gentables"))
  .settings(
    commonSettings,
    inThisBuild(List(
      organization := "virusdave",
      scalaVersion := ScalaVersion,
      version      := ProjectVersion
    )),
    name := "gentables",
    libraryDependencies ++= LibraryDependencies
  )
  .dependsOn(
    sloppy,
  )

lazy val db_gen = (project in file("db_gen"))
  .settings(
    commonSettings,
    inThisBuild(List(
      organization := "virusdave",
      scalaVersion := ScalaVersion,
      version      := ProjectVersion
    )),
    name := "db_gen",
    libraryDependencies ++= LibraryDependencies
  )
  .dependsOn(
    sloppy,
  )

lazy val db_all = project
  .aggregate(
    db_gen,
    gentables,
    sloppy,
  )

lazy val sloppy = {
  val commit = "9644276"
  ProjectRef(uri(s"https://github.com/virusdave/sloppy.git#${commit}"), "sloppy")
}

lazy val sloppy_example = (project in file("sloppy_example"))
  .settings(
    commonSettings,
    inThisBuild(List(
      organization := "virusdave",
      scalaVersion := ScalaVersion,
      version      := ProjectVersion
    )),
    name := "sloppy_example",
    libraryDependencies ++= LibraryDependencies,
  )
  .dependsOn(
    db_gen,
    sloppy,
  )

lazy val root = (project in file("."))
  .aggregate(
    db_gen,
    gentables,
    sloppy,
    sloppy_example,
  )
