name := "playInvoicing"

version := "1.0"

libraryDependencies := Seq[ModuleID](
  jdbc,
  cache,
  javaEbean.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.0.Final" withSources()
)

scalacOptions := Seq[String]("-feature", "-language:implicitConversions", "-language:postfixOps")

closureCompilerOptions += "ecmascript5"

ebeanEnabled := true

generateReverseRouter := false

playScalaSettings