package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import models.Department
import persists.DepartmentPersist
import schemas.inputs.DepartmentInput

import scala.util.{Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def create(input: DepartmentInput): Try[Department] = ValidateWith() {
    Success(Department(
      UUID.randomUUID(),
      "random name",
      Some("whatever")
    ))
  }

}
