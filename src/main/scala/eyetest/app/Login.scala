package eyetest.app

import subscript.language

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import eyetest.util._


class Login extends Frame with FrameProcess {

  title = "Eye test"
  location = new Point(300, 300)


  val userComboBox = new ComboBox(Nil)

  val testBtn      = new Button("Test")
  val registerBtn  = new Button("New user")
  val serializeBtn = new Button("Export to CSV")

  val controls = new GridPanel(1, 3) {
    contents += testBtn
    contents += registerBtn
    contents += serializeBtn
  }


  contents = new BorderPanel {
    layout(userComboBox) = Center
    layout(controls)     = South
  }

  script live = {..}

}