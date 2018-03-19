package persists

import models.Space

class SpacePersist {

  def findSpace(id: String): Option[Space] = Data.spaces find (_.id == id)

  def findAllSpaces: List[Space] = Data.spaces

}
