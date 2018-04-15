package facades

import java.util.UUID

import definitions.exceptions.PermissionException.PermissionNotFoundException
import models.Permission
import persists.PermissionPersist

import scala.util.Try

class PermissionFacade(permissionPersist: PermissionPersist) extends BaseFacade {

  def find(id: UUID): Try[Permission] = ValidateWith() {
    permissionPersist.find(id) toTry PermissionNotFoundException map Permission.of
  }

}
