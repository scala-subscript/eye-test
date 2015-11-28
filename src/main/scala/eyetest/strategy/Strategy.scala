package eyetest.strategy

/** A strategy for the test */
trait Strategy {

  /** Initial font to be used for the test */
  def initialFont(previousTestResult: Double): Int

  /** Whether the test is finished */
  def isFinished: Boolean

  /** Update the strategy with the latest guess data */
  def update(font: Int, guessed: Boolean): Unit

  /** Next font to be displayed to the user */
  def nextFont: Int

  /** Outputs the user's score at the end of the test */
  def success: Double

}

object Strategy {
  def simple(maxCorrectGuesses: Int = 5) = new SimpleStrategy(maxCorrectGuesses)
}