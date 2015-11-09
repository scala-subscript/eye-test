scalaVersion := "2.11.7"
libraryDependencies ++= Seq(
  "org.subscript-lang" %% "subscript-swing" % "2.0.0"
, "commons-io" % "commons-io" % "2.4"
, "org.apache.derby" % "derby" % "10.12.1.1"
)
SubscriptSbt.projectSettings

initialCommands := """
System.setSecurityManager(null)
val repo = new eyetest.data.derby.DerbyRepo("eyetest-db")
def res(s: subscript.vm.Script[_]): util.Try[_] = subscript.DSL._execute(s).$
"""