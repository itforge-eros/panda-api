package persists

import java.util.UUID

import entities.DepartmentEntity

trait DepartmentPersist {

  def find(id: UUID): Option[DepartmentEntity]

}
