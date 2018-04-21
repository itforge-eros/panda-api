package models.connections

import models.edges.MemberDepartmentEdge
import models.{BaseModel, Department, Identity}
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class MemberDepartmentConnection(@GraphQLExclude identity: Identity) extends BaseModel {

  @GraphQLField
  def edges(ctx: AppContext[MemberDepartmentConnection]): List[MemberDepartmentEdge] = {
    nodes(ctx) map (MemberDepartmentEdge(identity, _))
  }

  @GraphQLField
  def nodes(ctx: AppContext[MemberDepartmentConnection]): List[Department] = {
    identity.departments
  }

  @GraphQLField
  def totalCount(ctx: AppContext[MemberDepartmentConnection]) = {
    nodes(ctx).length
  }

}
