package eyetest

import scala.language.implicitConversions
import subscript.language

import eyetest.app._
import eyetest.data._
import eyetest.util._
import eyetest.util.Predef._

object Main extends SubScriptApplication {

  script live = (new Login(DummyRepositories))

  script foo = success: 1

}

object DummyRepositories extends Repositories {

  def user = new UserRepo {
    script all = success: Seq("Jack", "John", "Ann")
  }

}