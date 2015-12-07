package eyetest

import scala.language.implicitConversions
import subscript.language

import java.util.Date

import subscript.Predef._
import subscript.SubScriptApplication

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