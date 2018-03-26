package persists

import java.util.UUID

import models.Space

trait SpacePersist {

  def find(id: UUID): Option[Space]

  def findAll: List[Space]

  def insert(space: Space): Option[Space]

}
