package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait FormattingException {

  object WrongUuidFormatException
    extends Exception("Wrong UUID format.")
      with SafeException

}
