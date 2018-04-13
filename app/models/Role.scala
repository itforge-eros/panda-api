package models

import java.util.UUID

import entities.RoleEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class Role(id: UUID,
                name: String,
                description: Option[String],
                @GraphQLExclude departmentId: UUID) extends BaseModel {

  @GraphQLField
  def department(ctx: AppContext[Role]) = authorize(ctx) { implicit member =>
    ctx.ctx.departmentFacade.find(departmentId)
  }

}

object Role {

  def of(roleEntity: RoleEntity): Role = roleEntity.to[Role]()

}
