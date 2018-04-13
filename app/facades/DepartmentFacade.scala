package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.{CannotCreateDepartmentException, DepartmentNotFoundException}
import entities.DepartmentEntity
import models.Department
import persists.DepartmentPersist
import schemas.inputs.DepartmentInput

import scala.util.{Failure, Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def create(input: DepartmentInput): Try[Department] = ValidateWith() {
    val departmentEntity = DepartmentEntity(
      UUID.randomUUID(),
      input.name,
      input.description
    )

    departmentPersist.create(departmentEntity) match {
      case true => Success(departmentEntity) map Department.of
      case false => Failure(CannotCreateDepartmentException)
    }
  }

}
