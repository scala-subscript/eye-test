package eyetest.util

import subscript.language

import scala.swing.event._

import subscript.Predef._
import subscript.objectalgebra._
import subscript.swing.Scripts._


trait FrameProcess extends scala.swing.Frame with SSProcess {

  val closed = new Trigger

  override def closeOperation() {
    closed.trigger
  }

  implicit script vkey(??k: Key.Value) = vkey2: this, ??k

  override script lifecycle = @{there.onDeactivate(close())}: workflow / closed

  script workflow = let visible = true
                    var result: Any = null
                    super.lifecycle ~~(r: Any      )~~> let result = r
                                  +~/~(x: Throwable)~~> failure: x
                                  +~/~(null        )~~> [+]
                    success: result

}