package persists

import java.util.UUID

import entities.DepartmentEntity

trait DepartmentPersist {

  def find(id: UUID): Option[DepartmentEntity]

  def findByName(name: String): Option[DepartmentEntity]

  def findByMemberId(id: UUID): List[DepartmentEntity]

  def insert(departmentEntity: DepartmentEntity): Boolean

}
