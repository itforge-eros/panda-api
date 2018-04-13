package models

import java.util.UUID

import entities.GroupEntity
import henkan.convert.Syntax._

case class Group(id: UUID,
                 name: String,
                 description: Option[String]) extends BaseModel

object Group {

  def of(groupEntity: GroupEntity): Group = groupEntity.to[Group]()

}
