package schema.query

import models.{BaseModel, Member}
import sangria.macros.derive.GraphQLField

trait MemberQuery extends BaseModel {

  @GraphQLField
  def member(username: String)(ctx: AppContext[Unit]): Option[Member] = resolveOption {
    ctx.ctx.memberFacade.findByUsername(username)
  }

  @GraphQLField
  def me(ctx: AppContext[Unit]): Member = authorize(ctx) { implicit identity =>
    ctx.ctx.memberFacade.find(identity.viewer.id)
  }

}
