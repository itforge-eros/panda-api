package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object DepartmentException {

  object DepartmentNotFoundException
    extends Exception("Department not found.")
      with SafeException

  object DepartmentNameAlreadyTaken
    extends Exception("Department name already taken")
      with SafeException

  object CannotCreateDepartmentException
    extends Exception("Cannot create department.")
      with SafeException

}
