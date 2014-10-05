name := "playInvoicing"

version := "1.0"

libraryDependencies := Seq[ModuleID](
  jdbc,
  javaEbean.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.0.Final"
)

scalacOptions := Seq[String]("-feature", "-language:implicitConversions")

closureCompilerOptions += "ecmascript5"

ebeanEnabled := true

generateReverseRouter := false

playScalaSettings