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

  object SpaceCategoryNotFoundException
    extends Exception("Space category not found.")
      with SafeException

  object InvalidSpaceNameException
    extends Exception("Invalid space name. Only [A-Z], [a-z], [0-9], '-', '_' and '.' allowed and has at least 1 character.")
      with SafeException

  object InvalidSpaceFullNameException
    extends Exception("Invalid space full name. Must has at least 1 character.")
      with SafeException

}
