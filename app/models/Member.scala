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
  def requests(ctx: AppContext[Member]): List[Request] = authorize(ctx) { implicit identity =>
    ctx.ctx.memberFacade.requests(id)
  }

  @GraphQLField
  def reviews(ctx: AppContext[Member]): List[Review] = authorize(ctx) { implicit identity =>
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
    MemberDepartmentConnection(ctx.ctx.authenticationFacade.getIdentity(this))
  }

  @GraphQLField
  def accesses(ctx: AppContext[Member])(department: String): Option[List[Access]] = resolveOption {
    ctx.ctx.authorizationFacade.accesses(id, department)
  }

  @GraphQLField
  def isMe(ctx: AppContext[Member]): Boolean = authorize(ctx) { implicit identity =>
    Success(id == identity.viewer.id)
  }

}

object Member {

  def of(memberEntity: MemberEntity): Member = memberEntity.to[Member]()

}
