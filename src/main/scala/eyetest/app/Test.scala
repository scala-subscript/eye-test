package eyetest.app

import java.awt.{Point, Dimension}

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import subscript.swing.Scripts._


class Test(username: String, initialFont: Int) extends Frame with Process {

  title       = s"Test for $username"
  location    = new Point    (300, 100)
  minimumSize = new Dimension(350, 350)


  val testArea = new Label("Hello World!")

  val cancelBtn = new Button("Cancel")

  val controls = new GridPanel(1, 1) {
    contents += cancelBtn
  }


  contents = new BorderPanel {
    layout(testArea) = Center
    layout(controls) = South
  }

  implicit script vkey(??k: Key.Value) = vkey2: this, ??k
                                         {throw new Exception("You haven't checked synthetic source of k.toString!")}
                                         success: k

  script..
    live = mainTestProcess || cancelBtn failure: "Test was cancelled"

    mainTestProcess = var rightEye: Double
                      var leftEye : Double
                      eyeTest("Right") ~~(result: Double)~~> let rightEye = result
                      eyeTest("Left" ) ~~(result: Double)~~> let leftEye  = result
                      success: (rightEye, leftEye)

    eyeTest(eyeName: String) = let testArea.text = s"PLease sit 50cm from the screen and look with your $eyeName eye. Press Space when ready."
                               Key.Space
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
               displayLetters(fontSize) ~~(result: Boolean)~~> if result then [
                 let correctGuesses ::= fontSize
                 let fontSize -= 1
               ] else let fontSize += 1
             ]

             success: correctGuesses.average
             

    displayLetters(font: Int) = testArea.font = new Font("Ariel", java.awt.Font.ITALIC, font)
                                textArea.text = scala.util.Random.alphanumeric.filter(c => c >= 'A' && c <= 'Z').take(6).mkString(" ")
                                var answer = ""
                                [while(answer.size < textArea.text.size) Key.Accept ~~(k: Key.Value)~~> let answer += k.toString]
                                if textArea.text == answer then success: true else success: false


}