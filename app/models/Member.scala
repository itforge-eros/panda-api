package models

import java.util.UUID

import entities.MemberEntity
import henkan.convert.Syntax._
import sangria.macros.derive._

case class Member(id: UUID,
                  username: String,
                  firstName: String,
                  lastName: String,
                  email: String) extends BaseModel {

  @GraphQLField
  def requests(ctx: AppContext[Member]) = authorize(ctx) { implicit member =>
    ctx.ctx.memberFacade.requests(id)
  }

  @GraphQLField
  def reviews(ctx: AppContext[Member]) = authorize(ctx) { implicit member =>
    ctx.ctx.memberFacade.reviews(id)
  }

  @GraphQLField
  def reservations(ctx: AppContext[Member]) = resolve {
    ctx.ctx.memberFacade.reservations(id)
  }

  @GraphQLField
  def roles(ctx: AppContext[Member]) = resolve {
    ctx.ctx.memberFacade.roles(id)
  }

}

object Member {

  def of(memberEntity: MemberEntity): Member = memberEntity.to[Member]()

}
