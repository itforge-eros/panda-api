package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.{CannotCreateDepartmentException, DepartmentNotFoundException}
import entities.{DepartmentEntity, MemberRoleEntity, RoleEntity}
import models.{Department, Member, Role}
import persists.{DepartmentPersist, MemberRolePersist, RolePersist}
import schemas.inputs.DepartmentInput

import scala.util.{Failure, Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist,
                       rolePersist: RolePersist,
                       memberRolePersist: MemberRolePersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def roles(id: UUID): Try[List[Role]] = Validate() {
    rolePersist.findByDepartmentId(id) map Role.of
  }

  def create(input: DepartmentInput)
            (implicit member: Member): Try[Department] = ValidateWith() {
    val departmentId = UUID.randomUUID()
    val roleId = UUID.randomUUID()
    lazy val departmentEntity = DepartmentEntity(
      departmentId,
      input.name,
      input.description
    )
    lazy val ownerRoleEntity = RoleEntity(
      roleId,
      "Owner",
      Some("An owner of the department"),
      departmentId
    )
    lazy val ownerMemberRoleEntity = MemberRoleEntity(
      member.id,
      roleId
    )

    departmentPersist.insert(departmentEntity) &
      rolePersist.insert(ownerRoleEntity) &
      memberRolePersist.insert(ownerMemberRoleEntity) match {
      case true => Success(departmentEntity) map Department.of
      case false => Failure(CannotCreateDepartmentException)
    }
  }

}
