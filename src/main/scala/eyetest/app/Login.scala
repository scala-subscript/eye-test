package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import eyetest.data._
import eyetest.util._
import eyetest.util.Predef._


class Login(repositories: Repositories) extends Frame with FrameProcess {

  title = "Eye test"
  location = new Point(300, 300)


  val userComboBox = new ComboBox[String](Nil)
  def setUsers(users: Seq[String]) {
    mainPanel.layout(new ComboBox(users)) = Center
    peer.repaint()
    peer.revalidate()
  }

  val testBtn      = new Button("Test")
  val registerBtn  = new Button("New user")
  val serializeBtn = new Button("Export to CSV")

  val controlsPane = new GridPanel(1, 3) {
    contents += testBtn
    contents += registerBtn
    contents += serializeBtn
  }

  val mainPanel = new BorderPanel {
    layout(userComboBox) = Center
    layout(controlsPane) = South
  }

  contents = mainPanel


  script..
    live = init ; controls

    init = repositories.user ~~(users: Seq[String])~~> setUsers: users

    controls = {..}


}