package facades

import definitions.exceptions.PermissionException.PermissionNotFoundException
import models.Permission

import scala.util.Try

class PermissionFacade extends BaseFacade {

  def find(name: String): Try[Permission] = validateWith() {
    Permission(name) toTry PermissionNotFoundException
  }

  def permissions: List[Permission] = Permission.values

}
