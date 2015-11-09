package eyetest

import scala.language.implicitConversions
import subscript.language

import java.util.Date

import eyetest.app._
import eyetest.data._
import eyetest.util._
import eyetest.util.Predef._

object Main extends SubScriptApplication {

  script..
    live = initGui
           (new WaitScreen) || initData
           (new Login(DummyRepositories))

    initGui = javax.swing.UIManager.setLookAndFeel: "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
    initData = sleep: 1000


}

object DummyRepositories extends Repositories {

  def user = new UserRepo {script..
    all = success: Seq("Jack", "John", "Ann")
    write(username: String) = println("Registered " + username)
  }

  def score = new ScoreRepo {script..
    scoresOf(user: String): Any = success(for (i <- 1 to 10) yield (new Date, 15 - i / 10D, 16 - i / 10D))
    write(user: String, score: (Double, Double), date: Date) = println("Writing for " + user + " with score " + score + " on " + date)
  }

  override def close = ()

}