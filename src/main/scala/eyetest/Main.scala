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
    live = initGui
           [ new WaitScreen || initData^ ]~~(repos: Repositories)~~>
           [ new Login(repos); let repos.close()]

    initGui = javax.swing.UIManager.setLookAndFeel: "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"

    initData = let System.setSecurityManager(null)  // SecurityManager interferes with Derby; normally it is not needed
               ^new DerbyRepo("eyetest-db")
}