package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object ProblemException {

  object ProblemNotFoundException
    extends Exception("Problem not found.")
      with SafeException

  object CannotCreateProblemException
    extends Exception("Cannot create problem.")
      with SafeException

  object CannotUpdateProblemException
    extends Exception("Cannot update problem.")
      with SafeException

}
