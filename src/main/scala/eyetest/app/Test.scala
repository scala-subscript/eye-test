package eyetest.app

import subscript.language

import java.awt.{Point, Dimension}

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import subscript.swing.Scripts._

import eyetest.util._
import eyetest.util.Predef._


class Test(username: String, initialFont: Int, maxCorrectGuesses: Int) extends Frame with FrameProcess {

  title       = s"Test for $username"
  location    = new Point    (300, 100)
  minimumSize = new Dimension(350, 350)


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

    eyeTest(eyeName: String) = let testArea.text = s"Look with your $eyeName eye. Press Enter when ready."
                               sleep: 250  // So that if the user already holds Enter after the previous checkup, he has time to release it
                               Key.Enter
                               doTest ~~(result: Double)~~> success: result

    doTest = var fontSize = initialFont

             var previousResult = false
             displayLetters(fontSize) ~~(result: Boolean)~~> let previousResult = result
             [
               if previousResult then let fontSize -= 1 else let fontSize += 1
               displayLetters(fontSize) ~~(result: Boolean)~~> while(result == previousResult)
             ]
             
             var correctGuesses: List[Int] = Nil
             [
               while(correctGuesses.size < maxCorrectGuesses) 
               displayLetters(fontSize) ~~(result: Boolean)~~> [if result then [
                 let correctGuesses ::= fontSize
                 let fontSize -= 1
               ] else let fontSize += 1]
             ]

             success: (correctGuesses.sum / correctGuesses.size.toDouble)
             

    displayLetters(font: Int) = let answerField.text = ""
                                let testArea.font = new Font("Ariel", java.awt.Font.PLAIN, font)
                                var sample = scala.util.Random.alphanumeric.filter(c => c >= 'A' && c <= 'Z').take(6)
                                let testArea.text = sample.mkString(" ")
                                Key.Enter
                                var answer = answerField.text
                                println(answer + " == " + sample.mkString)
                                if sample.mkString == answer then success: true else success: false


}