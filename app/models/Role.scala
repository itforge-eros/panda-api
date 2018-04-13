package models

import java.util.UUID

import entities.RoleEntity
import henkan.convert.Syntax._
import sangria.macros.derive.GraphQLExclude

case class Role(id: UUID,
                name: String,
                description: Option[String],
                @GraphQLExclude departmentId: UUID)

object Role {

  def of(roleEntity: RoleEntity): Role = roleEntity.to[Role]()

}
