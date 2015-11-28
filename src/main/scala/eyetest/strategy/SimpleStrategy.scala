package eyetest.strategy

class SimpleStrategy(val maxCorrectGuesses: Int) extends Strategy {

  private var guesses: List[(Int, Boolean)] = Nil

  def initialFont(prev: Double) = prev.toInt

  // Finished when there are maxCorrectGuesses correct guesses
  def isFinished: Boolean =
    guesses.filter(_._2).size >= maxCorrectGuesses

  def update(font: Int, guessed: Boolean): Unit =
    guesses ::= font -> guessed

  def nextFont: Int =
    guesses.head match {case (previousFont, guessed) =>
      if (guessed) previousFont - 1 else previousFont + 1
    }

  def success: Double = {
    val correct = guesses.filter(_._2).map(_._1)
    correct.sum / correct.size.toDouble
  }
}