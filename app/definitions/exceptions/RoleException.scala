package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object RoleException {

  object RoleNotFoundException
    extends Exception("Role not found.")
      with SafeException

  object RoleNameAlreadyTaken
    extends Exception("Role name already taken.")
      with SafeException

  object CannotCreateRoleException
    extends Exception("Cannot create role example.")
      with SafeException

}
