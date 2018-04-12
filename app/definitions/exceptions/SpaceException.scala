package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait SpaceException {

  object SpaceNotFoundException
    extends Exception("Space not found.")
      with SafeException

  object CannotCreateSpaceException
    extends Exception("Cannot create space.")
      with SafeException

}
