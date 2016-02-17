package eyetest.strategy

/** A strategy for the test */
trait Strategy {
 
  private var _guesses: List[(Int, Boolean)] = Nil
  def guesses = _guesses

  /** A step of the font change during the calibration */
  def calibrationStep = 1

  /** Initial font to be used for the test */
  def initialFont(previousTestResult: Double): Int

  /** Whether the test is finished */
  def isFinished: Boolean

  /** Update the strategy with the latest guess data */
  def update(font: Int, guessed: Boolean): Unit =
    _guesses ::= font -> guessed

  /** Next font to be displayed to the user */
  def nextFont: Int

  /** Outputs the user's score at the end of the test */
  def success: Double

  def calibrateFontSize(oldSize: Int, goDown: Boolean): Int = {
    if (goDown) Math.max(  5, oldSize - calibrationStep)
    else        Math.min(200, oldSize + calibrationStep)
  }
}

object Strategy {
  def simple(maxCorrectGuesses: Int = 5) = new SimpleStrategy(maxCorrectGuesses)
  def batch (incrementStep: Int = 10, successGuessCount: Int = 5) = new BatchStrategy(incrementStep, successGuessCount)
}
