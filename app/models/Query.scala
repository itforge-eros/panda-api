package models

import java.util.UUID

import sangria.macros.derive.GraphQLField
import utils.GraphqlUtil.AppContext

class Query {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.space.find(id)

  @GraphQLField
  def spaces(ctx: AppContext[Unit]) = ctx.ctx.space.findAll

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.member.find(id)

  @GraphQLField
  def request(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.request.find(id)

}
