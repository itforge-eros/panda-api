package persists

import java.util.UUID

import entities.SpaceEntity

trait SpacePersist {

  def find(id: UUID): Option[SpaceEntity]

  def searchByName(name: String): List[SpaceEntity]

  def findByName(departmentName: String, spaceName: String): Option[SpaceEntity]

  def findByDepartmentId(departmentId: UUID): List[SpaceEntity]

  def findAll: List[SpaceEntity]

  def insert(space: SpaceEntity): Boolean

  def update(space: SpaceEntity): Boolean

}
