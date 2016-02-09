scalaVersion := "2.11.7"

version := "1.0.2"

assemblyJarName in assembly := "eye-test.jar"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.subscript-lang" %% "subscript-swing" % "3.0.2"
, "commons-io" % "commons-io" % "2.4"
, "org.apache.derby" % "derby" % "10.12.1.1"
)
SubscriptSbt.projectSettings

initialCommands := """
System.setSecurityManager(null)
val repo = new eyetest.data.derby.DerbyRepo("eyetest-db")
def res[T](s: subscript.vm.ScriptNode[T]): util.Try[T] = subscript.DSL._execute(s).$
"""