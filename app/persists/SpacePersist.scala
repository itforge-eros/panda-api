package persists

import java.util.UUID

import models.Space

trait SpacePersist {

  def findSpace(id: UUID): Option[Space]

  def findAllSpaces: List[Space]

}
