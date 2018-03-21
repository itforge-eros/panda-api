package spec

import persists.SpacePersist
import spec.data.SpaceData

class MockSpacePersist extends SpacePersist {

  override def findSpace(id: String) = SpaceData.spaces find (_.id == id)

  override def findAllSpaces = SpaceData.spaces

}
