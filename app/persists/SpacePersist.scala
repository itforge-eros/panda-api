package persists

import java.util.UUID

import entities.SpaceEntity

trait SpacePersist {

  def find(id: UUID): Option[SpaceEntity]

  def findAll: List[SpaceEntity]

  def insert(space: SpaceEntity): Option[SpaceEntity]

}
