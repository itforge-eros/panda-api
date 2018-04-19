package models.edges

import java.util.UUID

import models.enums.Access
import models.{BaseModel, Department, Permission, Role}
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class MemberDepartmentEdge(@GraphQLExclude memberId: UUID,
                                node: Department) extends BaseModel {

  @GraphQLField
  def roles(ctx: AppContext[MemberDepartmentEdge]): List[Role] = resolve {
    ctx.ctx.roleFacade.getRoles(memberId, node.id)
  }

  @GraphQLField
  def permissions(ctx: AppContext[MemberDepartmentEdge]): List[Permission] = resolve {
    ctx.ctx.roleFacade.getPermission(memberId, node.id)
  }

  @GraphQLField
  def accesses(ctx: AppContext[MemberDepartmentEdge]): List[Access] = resolve {
    ctx.ctx.roleFacade.getAccesses(memberId, node.id)
  }

}
