package eyetest.data

import subscript.language

import eyetest.util._
import eyetest.util.Predef._

trait UserRepo extends SSProcess {

  script..
    all: Any

    write(username: String): Any

    live = all
}