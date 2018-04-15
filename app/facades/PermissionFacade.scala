package facades

import definitions.exceptions.PermissionException.PermissionNotFoundException
import models.Permission

import scala.util.Try

class PermissionFacade extends BaseFacade {

  def findByName(name: String): Try[Permission] = ValidateWith() {
    Permission.values.find(name == _.name) toTry PermissionNotFoundException
  }

}
