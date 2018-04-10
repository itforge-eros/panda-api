package schemas.queries

import java.util.UUID

import models.{BaseModel, Member}
import sangria.macros.derive.GraphQLField

import scala.util.Try

trait MemberQuery extends BaseModel {

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]): Try[Member] =
    ctx.ctx.memberFacade.find(id)

  def me(ctx: AppContext[Unit]): Try[Member] =
    authorize(ctx) { implicit member =>
      ctx.ctx.memberFacade.find(member.id)
    }

}
