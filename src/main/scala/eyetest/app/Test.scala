package eyetest.app

import subscript.language

import java.awt.{Point, Dimension}

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._

import subscript.Predef._
import subscript.objectalgebra._
import subscript.swing.Scripts._

import eyetest.util._

import eyetest.strategy._


class Test(username: String, previousScoreRight: Double, previousScoreLeft: Double, maxCorrectGuesses: Int) extends Frame with FrameProcess {

  title       = s"Test for $username"
  location    = new Point    (300, 100)
  minimumSize = new Dimension(550, 350)

  var strategy: Strategy = null

  val testArea    = new Label("Hello World!")
  val answerField = new TextField("")
  val cancelBtn   = new Button("Cancel")

  val controls = new GridPanel(2, 1) {
    contents += answerField
    contents += cancelBtn

    answerField.font = answerField.font.deriveFont(44f)
    cancelBtn  .font = cancelBtn  .font.deriveFont(36f)
  }


  contents = new BorderPanel {
    layout(testArea) = Center
    layout(controls) = South
  }

  listenTo(answerField.keys)


  script..
    live = mainTestProcess^ / cancelBtn

    mainTestProcess = eyeTest("Right")^^1
                      eyeTest("Left" )^^2

    eyeTest(eyeName: String) = let testArea.font = new Font("Ariel", java.awt.Font.PLAIN, 20)
                               let testArea.text = s"Look with your $eyeName eye. Press Enter when ready."
                               sleep: 250  // So that if the user already holds Enter after the previous checkup, he has time to release it
                               Key.Enter
                               doTest( if(eyeName=="Right") previousScoreRight else previousScoreLeft )^

    doTest(previousFont: Double) =
      let strategy    = Strategy.batch()
      var fontSize    = strategy.initialFont(previousFont)

      println: "Calibrating" // get fontSize to the point where the user has hard times recognizing it
      displayLetters: fontSize ~~(firstResult: Boolean)~~> [
        let fontSize = strategy.calibrateFontSize(fontSize, firstResult)
        displayLetters: fontSize ~~(result: Boolean)~~> while (result==firstResult)
      ]

      println: "Testing"
      [
        while(!strategy.isFinished)
        displayLetters: fontSize ~~(result: Boolean)~~> strategy.update: fontSize, result
        let fontSize = strategy.nextFont
      ]

      ^strategy.success
             

    displayLetters(fontSize: Int) = let answerField.text = ""
                                    let testArea.font = new Font("Ariel", java.awt.Font.PLAIN, fontSize)
                                    var sample = scala.util.Random.alphanumeric.filter(c => c.isUpper).take(6)
                                    let testArea.text = sample.mkString(" ")
                                    Key.Enter
                                    var answer = answerField.text.toUpperCase
                                    println("Answer: " + answer + "; Correct: " + sample.mkString + "; Font: " + fontSize)
                                    ^(sample.mkString == answer)


}