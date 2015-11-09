package eyetest

import scala.language.implicitConversions
import subscript.language

import java.util.Date

import eyetest.util._
import eyetest.util.Predef._

import eyetest.app._
import eyetest.data._
import eyetest.data.derby._

object Main extends SubScriptApplication {

  script..
    live = var repos: Repositories = null
           
           initGui
           (new WaitScreen) || initData(?repos)
           (new Login(repos))

           let repos.close()

    initGui = javax.swing.UIManager.setLookAndFeel: "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"

    initData(?repos: Repositories) = let System.setSecurityManager(null)  // SecurityManager interferes with Derby; normally it is not needed
                                     let repos = new DerbyRepo("eyetest-db")

}

object DummyRepositories extends Repositories {

  def user = new UserRepo {script..
    all = success: Seq("Jack", "John", "Ann")
    write(username: String) = println("Registered " + username)
  }

  def score = new ScoreRepo {script..
    scoresOf(user: String): Any = success(for (i <- 1 to 10) yield (new Date, 15 - i / 10D, 16 - i / 10D))
    write(user: String, score: (Double, Double)) = println("Writing for " + user + " with score " + score)
  }

  override def close = ()

}