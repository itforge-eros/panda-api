package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait SpaceQuery extends BaseModel {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.spaceFacade.find(id)
  }

  @GraphQLField
  def spaces(ctx: AppContext[Unit]) = resolve {
    ctx.ctx.spaceFacade.findAll
  }

  @GraphQLField
  def searchSpaces(name: Option[String])(ctx: AppContext[Unit]) = resolve {
    name map ctx.ctx.spaceFacade.findByName getOrElse Success(Nil)
  }

}
