package eyetest.util

class Trigger {
  var listeners = List[() => Unit]()
  def trigger = listeners.foreach(_())
  def addListener(f: () => Unit) {listeners ::= f}
}