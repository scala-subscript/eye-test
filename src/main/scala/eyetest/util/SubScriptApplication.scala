package eyetest.util

trait SubScriptApplication extends SSProcess {

  def main(args: Array[String]): Unit = {
    subscript.DSL._execute(lifecycle)
    System.exit(0)
  }

}