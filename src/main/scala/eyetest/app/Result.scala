package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import subscript.swing.Scripts._

import eyetest.util._
import eyetest.util.Predef._

class Result(rightEye: Double, leftEye: Double) extends Frame with FrameProcess {

  title = "Result"
  location = new Point(300, 300)


  val rightEyeLbl = new Label(s"Right eye: $rightEye")
  val leftEyeLbl  = new Label(s"Left eye : $leftEye")

  val mainPanel = new GridPanel(2, 1) {
    contents += rightEyeLbl
    contents += leftEyeLbl
  }

  contents = mainPanel

  override script live = {..}

}