package eyetest.util

import subscript.language

object Predef {

  script..
    success(x  : Any   ) = {!x!}
    failure(msg: String) = {!throw new RuntimeException(msg)!}

}