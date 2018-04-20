package models

import java.util.UUID

import entities.MemberEntity
import facades.AuthorizationFacade
import henkan.convert.Syntax._
import models.connections.MemberDepartmentConnection
import models.enums.Access
import sangria.macros.derive._

import scala.util.Success

case class Member(id: UUID,
                  username: String,
                  firstName: String,
                  lastName: String,
                  email: String) extends BaseModel {

  @GraphQLField
  def requests(ctx: AppContext[Member]): List[Request] = authorize(ctx) { implicit viewer =>
    ctx.ctx.memberFacade.requests(id)
  }

  @GraphQLField
  def reviews(ctx: AppContext[Member]): List[Review] = authorize(ctx) { implicit viewer =>
    ctx.ctx.memberFacade.reviews(id)
  }

  @GraphQLField
  def reservations(ctx: AppContext[Member]): List[Reservation] = resolve {
    ctx.ctx.memberFacade.reservations(id)
  }

  @GraphQLField
  def roles(ctx: AppContext[Member]): List[Role] = resolve {
    ctx.ctx.memberFacade.roles(id)
  }

  @GraphQLField
  def departments(ctx: AppContext[Member]): MemberDepartmentConnection = {
    MemberDepartmentConnection(id)
  }

  @GraphQLField
  def accesses(ctx: AppContext[Member])(departmentId: UUID): List[Access] = resolve {
    ctx.ctx.authorizationFacade.accesses(departmentId, id)
  }

  @GraphQLField
  def isMe(ctx: AppContext[Member]): Boolean = authorize(ctx) { implicit viewer =>
    Success(id == viewer.id)
  }

  def accesses(authorizationFacade: AuthorizationFacade, departmentId: UUID): List[Access] = {
    authorizationFacade.accesses(id, departmentId).get
  }

}

object Member {

  def of(memberEntity: MemberEntity): Member = memberEntity.to[Member]()

}
