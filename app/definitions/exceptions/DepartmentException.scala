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

  object InvalidDepartmentNameException
    extends Exception("Invalid department name. Only [A-Z], [a-z], [0-9], '-', '_' and '.' allowed and has at least 1 character.")
      with SafeException

  object InvalidDepartmentFullNameException
    extends Exception("Invalid department full name. Must has at least 1 character.")
      with SafeException

}
