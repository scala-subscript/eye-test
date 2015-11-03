package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.awt.{Point, Dimension}

import scala.swing._
import scala.swing.event.Key
import scala.swing.BorderPanel.Position._

import subscript.swing.Scripts._

import eyetest.util._
import eyetest.util.Predef._


class WaitScreen extends Frame with FrameProcess {  
  title       = "Registration"
  location    = new Point(300, 300)
  minimumSize = new Dimension(350, 0)

  val progressBar = new ProgressBar {
    indeterminate = true
  }

  contents = new BorderPanel {
    layout(progressBar) = Center
  }

  script live = {..}
}