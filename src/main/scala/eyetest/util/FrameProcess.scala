package eyetest.util

import subscript.language

import eyetest.util.Predef._


trait FrameProcess extends scala.swing.Frame with SSProcess {

  val closed = new Trigger

  override def closeOperation() {
    closed.trigger
  }


  override script lifecycle = workflow / closed

  script workflow = let visible = true
                    var result: Any = null
                    super.lifecycle ~~(r: Any)~~> let result = r
                    close()
                    success: result

}