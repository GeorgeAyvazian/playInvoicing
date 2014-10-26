name := "playInvoicing"

version := "1.0"

libraryDependencies := Seq[ModuleID](
  jdbc,
  cache,
  javaEbean,
  javaJpa,
  "postgresql" % "postgresql" % "9.1-901.jdbc4"
)

scalacOptions := Seq[String]("-feature", "-language:implicitConversions", "-language:postfixOps")

closureCompilerOptions += "ecmascript5"

ebeanEnabled := true

generateReverseRouter := false

playScalaSettings