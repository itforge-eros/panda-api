package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait MemberQuery extends BaseModel {

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]) = resolve {
    ctx.ctx.memberFacade.find(id)
  }

  @GraphQLField
  def me(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.memberFacade.find(member.id)
  }

}
