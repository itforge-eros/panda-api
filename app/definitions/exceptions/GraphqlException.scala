package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object GraphqlException {

  object GraphqlVariablesParseError
    extends Exception("Cannot parse GraphQL variables.")
      with SafeException

  object TooComplexQueryError
    extends Exception("Query is too expensive.")
      with SafeException

}
