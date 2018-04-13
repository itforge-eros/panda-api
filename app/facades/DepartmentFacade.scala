package facades

import java.util.UUID

import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import models.Department
import persists.DepartmentPersist

import scala.util.Try

class DepartmentFacade(departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = ValidateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

}
