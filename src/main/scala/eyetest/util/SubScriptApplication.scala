package eyetest.util

import subscript.language

trait SubScriptApplication extends SSProcess {

  def main(args: Array[String]): Unit = subscript.DSL._execute(lifecycle)

}