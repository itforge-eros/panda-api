package schema.query

import java.util.UUID

import models.{BaseModel, Role}
import sangria.macros.derive.GraphQLField

trait RoleQuery extends BaseModel {

  @GraphQLField
  def role(id: UUID)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.roleFacade.find(id)
  }

}
