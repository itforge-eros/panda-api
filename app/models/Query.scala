package models

import java.util.UUID

import sangria.macros.derive.GraphQLField
import schemas.SchemaDefinition.AppContext

class Query {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]): Option[Space] =
    ctx.ctx.space.find(id)

  @GraphQLField
  def spaces(ctx: AppContext[Unit]): List[Space] =
    ctx.ctx.space.findAll

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]): Option[Member] =
    ctx.ctx.member.find(id)

}
