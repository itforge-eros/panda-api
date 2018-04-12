package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait RequestException {

  object RequestNotFoundException
    extends Exception("Request not found.")
      with SafeException

  object CannotCreateRequestException
    extends Exception("Cannot create request.")
      with SafeException

  object CannotCreateReviewException
    extends Exception("Cannot create review.")
      with SafeException

  object ReviewAlreadyExistedException
    extends Exception("Review already existed.")
      with SafeException

}
