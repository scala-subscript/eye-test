package eyetest.data

import subscript.language

import java.util.Date

import subscript.Predef._
import subscript.objectalgebra._

import eyetest.util._

trait ScoreRepo {

  object Col {
    val RIGHT_EYE = "RIGHT_EYE"
    val LEFT_EYE  = "LEFT_EYE"
    val USERNAME  = "USERNAME"
    val DATE      = "TEST_DATE" 
  }

  script..
    scoresOf(user: String): Any
    write   (user: String, score: (Double, Double)): Any

    last    (user: String) = scoresOf: user ~~(scores:    Seq[Score])~~> success:  scores.lastOption
    lastAvg (user: String) =     last: user ~~(score : Option[Score])~~> success: (score.map(s => (s._1+s._2)/2))

}
