package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import models.{Permission, Role}
import models.enums.Access
import models.enums.Access._
import persists.{DepartmentPersist, RolePersist}

import scala.util.Try

class AuthorizationFacade(rolePersist: RolePersist,
                          departmentPersist: DepartmentPersist) extends BaseFacade {

  def hasAccess(access: Access)(accesses: List[Access]): Boolean = getAccessValidator(access)(accesses)

  def roles(memberId: UUID, departmentId: UUID): Try[List[Role]] = validate() {
    val departmentRoles = rolePersist.findByDepartmentId(departmentId)
    val memberRoles = rolePersist.findByMemberId(memberId)

    departmentRoles intersect memberRoles map Role.of
  }

  def permissions(memberId: UUID, departmentId: UUID): Try[List[Permission]] = {
    roles(memberId, departmentId) map { roles =>
      roles flatMap (_.permissions) distinct
    }
  }

  def accesses(memberId: UUID, departmentId: UUID): Try[List[Access]] = {
    permissions(memberId, departmentId) map { permissions =>
      permissions flatMap (_.accesses) distinct
    }
  }

  def accesses(memberId: UUID, departmentName: String): Try[List[Access]] = {
    departmentPersist.findByName(departmentName)
      .toTry(DepartmentNotFoundException)
      .flatMap(department => accesses(memberId, department.id))
  }


  private def getAccessValidator(access: Access)(memberAccesses: List[Access]): Boolean = {
    memberAccesses.exists(_.name == access.name)
  }

}
