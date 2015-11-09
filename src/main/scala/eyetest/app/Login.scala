package eyetest.app

import subscript.language
import scala.language.implicitConversions

import java.util.Date
import java.io.File

import java.awt.Point

import scala.swing._
import scala.swing.BorderPanel.Position._

import subscript.swing.Scripts._

import eyetest.data._
import eyetest.util._
import eyetest.util.Predef._

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


  def getCurrentUser = userComboBox.peer.getSelectedItem.toString


  script..
    live = init ; controls


    init = initUsers
    initUsers = repositories.user ~~(users: Seq[String])~~> setUsers: users


    controls = controlsIter ...

    controlsIter = + [testBtn doTest          ]
                     [registerBtn doRegister  ]
                     [serializeBtn doSerialize]

    doTest = let visible = false

             {!getCurrentUser!} ~~(currentUser: String)~~> [
               [repositories.score.lastAvg: currentUser ~~(score: Option[Double])~~> (new Test(currentUser, score.map(_.toInt).getOrElse(20), 5))]
               ~~(result: (Double, Double))~~> repositories.score.write: currentUser, result
             ]

             let visible = true


    doRegister = let visible = false
                 (new Register) ~~(name: String)~~> repositories.user.write: name
                 initUsers
                 let visible = true

    doSerialize = selectFile ~~(file: File)~~> [repositories.score.scoresOf: getCurrentUser ~~(scores: Seq[Score])~~> [
      val csv = "Date,Right Eye,Left Eye\n" + scores.map {case (right, left, date) =>
        val formated = new java.text.SimpleDateFormat("dd.MM.yyyy").format(date)   // SimpleDateFormat to be abstracted to a separate var as soon as local vars can be used from other local vars' definitions
        s"$formated,$right,$left"}.mkString("\n")
      FileUtils.write: file, csv
    ]]

    selectFile = val chooser = new FileChooser
                 if chooser.showSaveDialog(null) == FileChooser.Result.Approve then success: chooser.selectedFile

}