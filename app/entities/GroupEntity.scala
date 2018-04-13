package entities

import java.util.UUID

case class GroupEntity(id: UUID,
                       name: String,
                       description: Option[String])
