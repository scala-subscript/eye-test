package eyetest.strategy

/**
 * Success happens when the user gives 5 correct answers.
 * Doesn't work: luck may come into play and the user may guess
 * much less then they can actually see. This lucky guess can
 * affect average of all the correct guesses greatly.
 */
class SimpleStrategy(val maxCorrectGuesses: Int) extends Strategy {

  def initialFont(prev: Double) = prev.toInt

  // Finished when there are maxCorrectGuesses correct guesses
  def isFinished: Boolean =
    guesses.filter(_._2).size >= maxCorrectGuesses

  def nextFont: Int =
    guesses.head match {case (previousFont, guessed) =>
      if (guessed) previousFont - 1 else previousFont + 1
    }

  def success: Double = {
    val correct = guesses.filter(_._2).map(_._1)
    correct.sum / correct.size.toDouble
  }
}