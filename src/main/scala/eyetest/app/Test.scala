package eyetest.app

import subscript.language

import java.awt.{Point, Dimension}

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import subscript.swing.Scripts._

import eyetest.util._
import eyetest.util.Predef._

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
  }


  contents = new BorderPanel {
    layout(testArea) = Center
    layout(controls) = South
  }

  listenTo(answerField.keys)


  script..
    live = mainTestProcess || cancelBtn

    mainTestProcess = var rightEye: Double = 0
                      var leftEye : Double = 0
                      eyeTest("Right") ~~(result: Double)~~> let rightEye = result
                      eyeTest("Left" ) ~~(result: Double)~~> let leftEye  = result
                      success: (rightEye, leftEye)

    eyeTest(eyeName: String) = let testArea.font = new Font("Ariel", java.awt.Font.PLAIN, 20)
                               let testArea.text = s"Look with your $eyeName eye. Press Enter when ready."
                               sleep: 250  // So that if the user already holds Enter after the previous checkup, he has time to release it
                               Key.Enter
                               doTest(if (eyeName == "Right") previousScoreRight else previousScoreLeft) ~~(result: Double)~~> success: result

    doTest(previousFont: Double) =
      let strategy = Strategy.batch()
      var fontSize = strategy.initialFont(previousFont)

      var previousResult = false
      var currentResult  = false
      displayLetters(fontSize) ~~(result: Boolean)~~> [
       let previousResult = result
       let currentResult  = result
      ]

      // Calibrating: get fontSize to the point where the user has hard times recognizing it
      [
        while(previousResult == currentResult)
        if previousResult then let fontSize -= strategy.calibrationStep else let fontSize += strategy.calibrationStep
        displayLetters(fontSize) ~~(result: Boolean)~~> [
          let previousResult = currentResult
          let currentResult  = result
        ]
      ]

      // Testing
      [
        while(!strategy.isFinished) 
        displayLetters(fontSize) ~~(result: Boolean)~~> [
          strategy.update(fontSize, result)
          let fontSize = strategy.nextFont
        ]
      ]

      success: strategy.success
             

    displayLetters(font: Int) = let answerField.text = ""
                                let testArea.font = new Font("Ariel", java.awt.Font.PLAIN, font)
                                var sample = scala.util.Random.alphanumeric.filter(c => c >= 'A' && c <= 'Z').take(6)
                                let testArea.text = sample.mkString(" ")
                                Key.Enter
                                var answer = answerField.text
                                println("Answer: " + answer + "; Correct: " + sample.mkString + "; Font: " + font)
                                if sample.mkString == answer then success: true else success: false


}