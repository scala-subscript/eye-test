package eyetest.util

import subscript.language

object Predef {

  script..
    success(x  : Any      ) = {!x!}

    failure(msg: String   ): Any = {!throw new RuntimeException(msg)!}
    failure(t  : Throwable): Any = {!throw t!}

    sleep(t: Long) = {* Thread.sleep(t) *}

  implicit script..
    process2script(p: SSProcess) = p.lifecycle
    trigger2script(t: Trigger  ) = @{t.addListener {() => there.codeExecutor.executeAA}}: {. .}
}