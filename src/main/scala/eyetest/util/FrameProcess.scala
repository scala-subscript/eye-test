package eyetest.util

import subscript.language

import eyetest.util.Predef._


trait FrameProcess extends SSProcess {this: scala.swing.Frame =>

  override script lifecycle = let visible = true
                              var result: Any = null
                              var exception: Throwable = null

                              super.lifecycle ~~(r: Any      )~~> let result    = r
                                            +~/~(t: Throwable)~~> let exception = t

                              close()

                              if result != null then success: result
                              else failure: exception

}