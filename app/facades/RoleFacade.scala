package facades

import java.util.UUID

import definitions.exceptions.RoleException.RoleNotFoundException
import models.Role
import persists.RolePersist

class RoleFacade(rolePersist: RolePersist) extends BaseFacade {

  def find(id: UUID) = ValidateWith() {
    rolePersist.find(id) toTry RoleNotFoundException map Role.of
  }

}
