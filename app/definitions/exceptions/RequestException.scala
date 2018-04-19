package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object RequestException {

  object RequestNotFoundException
    extends Exception("Request not found.")
      with SafeException

  object CannotCreateRequestException
    extends Exception("Cannot create request.")
      with SafeException

  object CannotCancelRequestException
    extends Exception("Cannot cancel request.")
      with SafeException

  object RequestAlreadyClosedException
    extends Exception("Request already closed.")
      with SafeException

  class NegativePeriodException(range: Range)
    extends Exception(s"Invalid period. Period must be positive. Got [${range.start}, ${range.end}).")

}
