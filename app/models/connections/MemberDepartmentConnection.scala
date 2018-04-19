package models.connections

import java.util.UUID

import models.{BaseModel, Department}
import models.edges.MemberDepartmentEdge
import sangria.macros.derive.GraphQLField

case class MemberDepartmentConnection(memberId: UUID) extends BaseModel {

  @GraphQLField
  def edges(ctx: AppContext[MemberDepartmentConnection]): List[MemberDepartmentEdge] = {
    nodes(ctx) map (MemberDepartmentEdge(memberId, _))
  }

  @GraphQLField
  def nodes(ctx: AppContext[MemberDepartmentConnection]): List[Department] = resolve {
    ctx.ctx.memberFacade.departments(memberId)
  }

}
