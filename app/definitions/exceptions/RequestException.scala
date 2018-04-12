package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait RequestException {

  object RequestNotFoundException
    extends Exception("Request not found.")
    with SafeException

  object CannotCreateRequestException
    extends Exception("Cannot create request.")
    with SafeException

}
