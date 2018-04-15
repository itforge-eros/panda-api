package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object FormattingException {

  object WrongUuidFormatException
    extends Exception("Wrong UUID format.")
      with SafeException

}
