package facades

import java.util.UUID

import definitions.exceptions.RoleException.RoleNotFoundException
import models.{Member, Role}
import persists.{MemberPersist, RolePersist}

class RoleFacade(rolePersist: RolePersist,
                 memberPersist: MemberPersist) extends BaseFacade {

  def find(id: UUID) = ValidateWith() {
    rolePersist.find(id) toTry RoleNotFoundException map Role.of
  }

  def members(id: UUID) = Validate() {
    memberPersist.findByRoleId(id) map Member.of
  }

}
