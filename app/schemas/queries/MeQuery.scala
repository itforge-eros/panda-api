package schemas.queries

import models.{BaseModel, Member}
import sangria.macros.derive.GraphQLField

import scala.util.Try

trait MeQuery extends BaseModel {

  @GraphQLField
  def me(ctx: AppContext[Unit]): Try[Member] =
    authorize(ctx) { implicit member =>
      ctx.ctx.memberFacade.find(member.id)
    }

}
