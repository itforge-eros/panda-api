package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object DepartmentException {

  object DepartmentNotFoundException
    extends Exception("Department not found.")
      with SafeException

}
