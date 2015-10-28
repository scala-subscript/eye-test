package eyetest.util

import subscript.language

trait SSProcess {
  
  script..

    /** To be overriden by the user */
    live: Any

    /** Enhancements to "live" go here */
    lifecycle = live
    
}