package eyetest.strategy

/**
 * Success happens when the user gives a certain number of
 * consecutive correct answers.
 */
class BatchStrategy(_incrementStep: Int, val successGuessCount: Int) extends Strategy {

  // Must be at least successGuessCount, so that the user doesn't enter
  // an infinite loop where he can't score the required number
  // of consequitive correct guesses.
  val incrementStep = math.max(_incrementStep, successGuessCount)

  override val calibrationStep = incrementStep

  /**
   * Increments the previous font by incrementStep so that
   * the first letters are clearly visible to the user.
   */
  def initialFont(previousTestResult: Double): Int =
    previousTestResult.toInt + incrementStep

  /**
   * The test is finished when the last guess is false
   * and there were at least successGuessCount true guesses
   * before it.
   */
  def isFinished: Boolean = {
    val validity = guesses.map(_._2)
    validity.headOption.map {last =>
      val previous = validity.drop(1).take(successGuessCount)
      !last && previous.size >= successGuessCount && previous.forall(x => x)
    }.getOrElse(false)
  }

  /**
   * If the last guess is correct, decrement the font by 1,
   * else increment by incrementStep.
   */
  def nextFont: Int = guesses.head match {
    case (font, guess) => if (guess) font - 1 else font + incrementStep
  }

  /**
   * The average of the last successGuessCount true guesses.
   */
  def success: Double = {
    val correct = guesses.filter(_._2).take(successGuessCount).map(_._1)
    correct.sum / correct.size.toDouble
  }


}
