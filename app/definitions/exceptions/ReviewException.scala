package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object ReviewException {

  object ReviewNotFoundException
    extends Exception("Review not found.")
      with SafeException

  object CannotCreateReviewException
    extends Exception("Cannot create review.")
      with SafeException

}
