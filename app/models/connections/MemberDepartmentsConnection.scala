package models.connections

import java.util.UUID

import models.BaseModel
import models.edges.MemberDepartmentsEdge
import sangria.macros.derive.GraphQLField

case class MemberDepartmentsConnection(memberId: UUID) extends BaseModel {

  @GraphQLField
  def edges(ctx: AppContext[MemberDepartmentsConnection]): List[MemberDepartmentsEdge] = ???

}
