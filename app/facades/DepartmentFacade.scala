package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.{CannotCreateDepartmentException, DepartmentNotFoundException}
import entities.{DepartmentEntity, RoleEntity}
import models.{Department, Role}
import persists.{DepartmentPersist, RolePersist}
import schemas.inputs.DepartmentInput

import scala.util.{Failure, Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist,
                       rolePersist: RolePersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def roles(id: UUID): Try[List[Role]] = Validate() {
    rolePersist.findByDepartmentId(id) map Role.of
  }

  def create(input: DepartmentInput): Try[Department] = ValidateWith() {
    val departmentId = UUID.randomUUID()
    lazy val departmentEntity = DepartmentEntity(
      departmentId,
      input.name,
      input.description
    )
    lazy val adminRoleEntity = RoleEntity(
      UUID.randomUUID(),
      "Owner",
      Some("An owner of the department"),
      departmentId
    )

    departmentPersist.insert(departmentEntity) &
      rolePersist.insert(adminRoleEntity) match {
      case true => Success(departmentEntity) map Department.of
      case false => Failure(CannotCreateDepartmentException)
    }
  }

}
