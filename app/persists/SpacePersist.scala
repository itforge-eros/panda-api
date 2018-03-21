package persists

import models.Space

trait SpacePersist {

  def findSpace(id: String): Option[Space]

  def findAllSpaces: List[Space]

}
