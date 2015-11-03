package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.util.Date

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import subscript.swing.Scripts._

import eyetest.data._
import eyetest.util._
import eyetest.util.Predef._


class Login(repositories: Repositories) extends Frame with FrameProcess {

  title = "Eye test"
  location = new Point(300, 300)


  var userComboBox = new ComboBox[String](Nil)
  def setUsers(users: Seq[String]) {
    userComboBox = new ComboBox(users)
    mainPanel.layout(userComboBox) = Center
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


    init = initUsers
    initUsers = repositories.user ~~(users: Seq[String])~~> setUsers: users


    controls = controlsIter ...

    controlsIter = + [testBtn doTest          ]
                     [registerBtn doRegister  ]
                     [serializeBtn doSerialize]

    doTest = var currentUser = userComboBox.peer.getSelectedItem.toString
             let visible = false

             {!userComboBox.peer.getSelectedItem.toString!} ~~(currentUser: String)~~> [
               [repositories.score.lastAvg: currentUser ~~(score: Double)~~> (new Test(currentUser, score.toInt, 5))]
               ~~(result: (Double, Double))~~> repositories.score.write: currentUser, result, new Date
             ]

             let visible = true

    doRegister = let visible = false
                 (new Register) ~~(name: String)~~> repositories.user.write: name
                 initUsers
                 let visible = true

    doSerialize = println: "I serialize things"

}