package eyetest

import scala.language.implicitConversions
import subscript.language

import eyetest.app._
import eyetest.util._
import eyetest.util.Predef._

object Main extends SubScriptApplication {

  script live = (new Login)

}