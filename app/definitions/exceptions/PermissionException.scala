package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object PermissionException {

  object PermissionNotFoundException
    extends Exception("Permission not found.")
      with SafeException

}
