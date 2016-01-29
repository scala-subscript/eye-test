package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.util.Date
import java.io.File

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import subscript.Predef._
import subscript.objectalgebra._
import subscript.swing.Scripts._

import eyetest.data._
import eyetest.util._

import org.apache.commons.io.FileUtils


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

  val testBtn      = new Button("Test"         ) {enabled = false}
  val registerBtn  = new Button("New user"     ) {enabled = false}
  val serializeBtn = new Button("Export to CSV") {enabled = false}

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


  def getCurrentUser = userComboBox.peer.getSelectedItem.toString


  script..
    live      = initUsers; controlAction...

    initUsers = repositories.user ~~(users: Seq[String])~~> setUsers: users

    controlAction = + [userIsSelected testBtn doTest]
                      [registerBtn doRegister  ]
                      [userIsSelected serializeBtn doSerialize]

    userIsSelected = guard: userComboBox, {() => userComboBox.peer.getSelectedItem != null}

    doTest = let visible = false

             ^getCurrentUser
               ~~(null)~~> [+-]
              +~~(currentUser: String)~~> [
                 repositories.score.last: currentUser
                 ~~(Some((right:Double, left:Double,_)))~~> new Test(currentUser, right, left, 5)
                +~~(None                               )~~> new Test(currentUser, 20   , 20  , 5)
               ]
               ~~((right:Double, left:Double))~~> [ repositories.score.write: currentUser, (right, left)
                                                    new Result(right, left)
                                                  ]
             let visible = true


    doRegister = let visible = false
                 new Register ~~(name: String)~~> repositories.user.write: name
                             +~~(null)~~> [+]
                 initUsers
                 let visible = true

    doSerialize = selectFile ~~(null)~~> [+]
                            +~~(file: File)~~> repositories.score.scoresOf: getCurrentUser
                             ~~(scores: Seq[Score])~~> [val header = "Date,Right Eye,Left Eye\n"
                                                        val csv = scores.map {case (right, left, date) =>
                                                          // SimpleDateFormat to be abstracted to a separate var as soon as
                                                          // local vars can be used from other local vars' definitions
                                                          val dateStr = new java.text.SimpleDateFormat("dd.MM.yyyy").format(date)
                                                          s"$dateStr,$right,$left"
                                                        }.mkString("\n")
                                                        FileUtils.write: file, (header+csv)
                                                       ]

    selectFile = val chooser = new FileChooser
                 if chooser.showSaveDialog(null)==FileChooser.Result.Approve then ^chooser.selectedFile

}