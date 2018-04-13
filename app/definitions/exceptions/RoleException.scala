package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object RoleException {

  object RoleNotFoundException
    extends Exception("Role not found.")
      with SafeException

}
