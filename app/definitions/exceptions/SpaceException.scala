package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object SpaceException {

  object SpaceNotFoundException
    extends Exception("Space not found.")
      with SafeException

  object SpaceNameAlreadyTaken
    extends Exception("Space name already taken.")
      with SafeException

  object CannotCreateSpaceException
    extends Exception("Cannot create space.")
      with SafeException

  object CannotUpdateSpaceException
    extends Exception("Cannot update space.")
      with SafeException

}
