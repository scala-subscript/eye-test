package eyetest.data

import subscript.language

import java.util.Date

import eyetest.util._
import eyetest.util.Predef._

trait ScoreRepo {

  object Col {
    val RIGHT_EYE = "RIGHT_EYE"
    val LEFT_EYE  = "LEFT_EYE"
    val USERNAME  = "USERNAME"
    val DATE      = "TEST_DATE" 
  }

  script..
    scoresOf(user: String): Any

    last(user: String) = scoresOf: user ~~(scores: Seq[Score])~~> success: scores.last

    lastAvg(user: String) = last: user ~~(score: Score)~~> success((score._1 + score._2) / 2)

    write(user: String, score: (Double, Double)): Any
}