package persists

import java.util.UUID

import entities.MaterialEntity

trait MaterialPersist {

  def find(id: UUID): Option[MaterialEntity]

  def findByDepartmentId(departmentId: UUID): List[MaterialEntity]

  def create(materialEntity: MaterialEntity): Boolean

}
