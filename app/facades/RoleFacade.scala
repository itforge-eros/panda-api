package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.RoleException.{CannotCreateRoleException, RoleNameAlreadyTaken, RoleNotFoundException}
import entities.RoleEntity
import models.inputs.CreateRoleInput
import models.{Member, Role}
import persists.{DepartmentPersist, MemberPersist, RolePersist}

import scala.util.{Failure, Success, Try}

class RoleFacade(rolePersist: RolePersist,
                 memberPersist: MemberPersist,
                 departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Role] = validateWith() {
    rolePersist.find(id) toTry RoleNotFoundException map Role.of
  }

  def findByName(department: String, name: String): Try[Role] = validateWith() {
    rolePersist.findByName(department, name) toTry RoleNotFoundException map Role.of
  }

  def members(id: UUID): Try[List[Member]] = validate() {
    memberPersist.findByRoleId(id) map Member.of
  }

  def create(input: CreateRoleInput)
            (implicit member: Member): Try[Role] = {
    lazy val maybeDepartmentEntity = departmentPersist.find(input.departmentId)
    lazy val roleEntity = RoleEntity(
      UUID.randomUUID(),
      input.name,
      input.description,
      input.permissions.distinct,
      input.departmentId
    )

    validateWith(
      Guard(maybeDepartmentEntity.isEmpty, DepartmentNotFoundException),
      Guard(rolePersist.findByName(maybeDepartmentEntity.get.name, input.name).isDefined, RoleNameAlreadyTaken)
    ) {
      rolePersist.insert(roleEntity) match {
        case true => Success(roleEntity) map Role.of
        case false => Failure(CannotCreateRoleException)
      }
    }
  }

}
