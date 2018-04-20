package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object PermissionException {

  class PermissionNotFoundException(name: String)
    extends Exception(s"Permission '$name' not found.")
      with SafeException

}
