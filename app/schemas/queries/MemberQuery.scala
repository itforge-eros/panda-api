package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait MemberQuery extends BaseModel {

  @GraphQLField
  def member(username: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.memberFacade.findByUsername(username)
  }

  @GraphQLField
  def me(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.memberFacade.find(member.id)
  }

}
