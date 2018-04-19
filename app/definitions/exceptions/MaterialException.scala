package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object MaterialException {

  object MaterialNotFoundException
    extends Exception("Material not found.")
      with SafeException

  object CannotCreateMaterialException
    extends Exception("Cannot create material.")
      with SafeException

}
