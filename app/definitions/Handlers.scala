package definitions

import definitions.AppException.TooComplexQueryError
import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}

object Handlers {

  lazy val exceptionHandler = ExceptionHandler {
    case (_, error @ TooComplexQueryError) ⇒ HandledException(error.getMessage)
    case (_, error @ MaxQueryDepthReachedError(_)) ⇒ HandledException(error.getMessage)
  }

}
