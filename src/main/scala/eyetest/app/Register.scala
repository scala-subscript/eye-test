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


class Register extends Frame with FrameProcess {
  title       = "Registration"
  location    = new Point(300, 300)
  minimumSize = new Dimension(350, 0)


  val usernameField = new TextField
  val registerBtn   = new Button("Register")

  contents = new BorderPanel {
    layout(usernameField) = Center
    layout(registerBtn  ) = South
  }

  listenTo(usernameField.keys)


  script..
    live = registerBtn + Key.Enter
           success: usernameField.text
}