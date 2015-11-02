package eyetest.data

import subscript.language

import java.util.Date

import eyetest.util._
import eyetest.util.Predef._

trait ScoreRepo {

  script..
    scoresOf(user: String): Any

    last(user: String) = scoresOf: user ~~(scores: Seq[(Date, Double, Double)])~~> success: scores.last

    lastAvg(user: String) = last: user ~~(score: (Date, Double, Double))~~> success((score._2 + score._3) / 2)

    write(user: String, score: (Double, Double), date: Date): Any
}