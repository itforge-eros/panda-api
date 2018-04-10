package spec.mockpersists

import java.util.UUID

import entities.SpaceEntity
import persists.SpacePersist
import spec.data.SpaceData

class MockSpacePersist extends SpacePersist {

  override def find(id: UUID) = SpaceData.spaces find (_.id == id)

  override def findAll = SpaceData.spaces

  override def insert(space: SpaceEntity) = true

}
