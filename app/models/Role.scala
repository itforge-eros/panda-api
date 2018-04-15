package models

import java.util.UUID

import entities.RoleEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class Role(id: UUID,
                name: String,
                description: Option[String],
                permissions: List[Permission],
                @GraphQLExclude departmentId: UUID) extends BaseModel {

  @GraphQLField
  def department(ctx: AppContext[Role]) = resolve {
    ctx.ctx.departmentFacade.find(departmentId)
  }

  @GraphQLField
  def members(ctx: AppContext[Role]) = resolve {
    ctx.ctx.roleFacade.members(id)
  }

}

object Role {

  def of(roleEntity: RoleEntity): Role = Role(
    roleEntity.id,
    roleEntity.name,
    roleEntity.description,
    roleEntity.permissions flatMap Permission.fromName,
    roleEntity.departmentId
  )

}
