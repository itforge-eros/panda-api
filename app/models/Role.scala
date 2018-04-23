package models

import java.time.Instant
import java.util.UUID

import entities.RoleEntity
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class Role(id: UUID,
                name: String,
                description: Option[String],
                permissions: List[Permission],
                createdAt: Instant,
                updatedAt: Instant,
                @GraphQLExclude departmentId: UUID) extends BaseModel {

  @GraphQLField
  def department(ctx: AppContext[Role]): Department = resolve {
    ctx.ctx.departmentFacade.find(departmentId)
  }

  @GraphQLField
  def members(ctx: AppContext[Role]): List[Member] = resolve {
    ctx.ctx.roleFacade.members(id)
  }

}

object Role {

  def of(roleEntity: RoleEntity): Role = Role(
    roleEntity.id,
    roleEntity.name,
    roleEntity.description,
    roleEntity.permissions map Permission.apply map (_.get),
    roleEntity.createdAt,
    roleEntity.updatedAt,
    roleEntity.departmentId
  )

}
