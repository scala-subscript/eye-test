package eyetest

import subscript.language

import eyetest.app._
// import eyetest.util._

object Main {
  def main(args: Array[String]) {
    val l = new Test("Me", 20, 5) {
      override def closeOperation() {System.exit(0)}
    }
    l.visible = true
  }
}