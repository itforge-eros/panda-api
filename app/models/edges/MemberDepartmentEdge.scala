package models.edges

import models._
import models.enums.Access
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class MemberDepartmentEdge(@GraphQLExclude identity: Identity,
                                node: Department) extends BaseModel {

  @GraphQLField
  def roles(ctx: AppContext[MemberDepartmentEdge]): List[Role] = {
    identity.department(node.id).get.roles
  }

  @GraphQLField
  def permissions(ctx: AppContext[MemberDepartmentEdge]): List[Permission] = {
    identity.department(node.id).get.permissions
  }

  @GraphQLField
  def accesses(ctx: AppContext[MemberDepartmentEdge]): List[Access] = {
    identity.department(node.id).get.accesses
  }

}
