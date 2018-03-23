package spec

import java.util.UUID

import persists.SpacePersist
import spec.data.SpaceData

class MockSpacePersist extends SpacePersist {

  override def findSpace(id: UUID) = SpaceData.spaces find (_.id == id)

  override def findAllSpaces = SpaceData.spaces

}
