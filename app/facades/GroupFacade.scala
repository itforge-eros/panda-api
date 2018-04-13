package facades

import java.util.UUID

import models.Group

import scala.util.Try

class GroupFacade extends BaseFacade {

  def find(id: UUID): Try[Group] = Validate() {
    Group(
      UUID.randomUUID(),
      "group name",
      Some("group description")
    )
  }

}
