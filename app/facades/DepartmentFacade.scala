package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.{CannotCreateDepartmentException, DepartmentNameAlreadyTaken, DepartmentNotFoundException}
import entities.{DepartmentEntity, MemberRoleEntity, RoleEntity}
import models.Permission.AdminAccessPermission
import models.inputs.CreateDepartmentInput
import models.{Department, Member, Role, Space}
import persists.{DepartmentPersist, MemberRolePersist, RolePersist, SpacePersist}

import scala.util.{Failure, Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist,
                       rolePersist: RolePersist,
                       memberRolePersist: MemberRolePersist,
                       spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def findByName(name: String): Try[Department] = ValidateWith() {
    departmentPersist.findByName(name) toTry DepartmentNotFoundException map Department.of
  }

  def roles(id: UUID): Try[List[Role]] = Validate() {
    rolePersist.findByDepartmentId(id) map Role.of
  }

  def spaces(id: UUID): Try[List[Space]] = Validate() {
    spacePersist.findByDepartmentId(id) map Space.of
  }

  def create(input: CreateDepartmentInput)
            (implicit member: Member): Try[Department] = ValidateWith(
    Guard(departmentPersist.findByName(input.name).isDefined, DepartmentNameAlreadyTaken)
  ) {
    val departmentId = UUID.randomUUID()
    val roleId = UUID.randomUUID()
    lazy val departmentEntity = DepartmentEntity(
      departmentId,
      input.name,
      input.fullEnglishName,
      input.fullThaiName,
      input.description
    )
    lazy val ownerRoleEntity = RoleEntity(
      roleId,
      "Owner",
      Some("An owner of the department"),
      List(AdminAccessPermission.name),
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
