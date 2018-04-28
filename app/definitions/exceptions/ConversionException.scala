package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object ConversionException {

  object InvalidDateFormatException
    extends Exception(s"Invalid date format.")
      with SafeException

}
