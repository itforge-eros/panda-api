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

  object CannotDeleteSpaceException
    extends Exception("Cannot delete space.")
      with SafeException

  object InvalidSpaceNameException
    extends Exception("Invalid space name. Only [A-Z], [a-z], [0-9], '-', '_' and '.' allowed and has at least 1 character.")
      with SafeException

  object InvalidSpaceFullNameException
    extends Exception("Invalid space full name. Must has at least 1 character.")
      with SafeException

  object InvalidSpaceTag
    extends Exception("Invalid space tag. Only [a-z], [0-9] and '-' allowed and has at least 1 character.")
      with SafeException

  class InvalidSearchPrefix(invalidPrefixes: List[String])
    extends Exception(s"Invalid prefix: [${invalidPrefixes.mkString(", ")}]")
      with SafeException

}
