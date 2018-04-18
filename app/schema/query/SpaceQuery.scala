package schema.query

import models.BaseModel
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait SpaceQuery extends BaseModel {

  @GraphQLField
  def space(department: String, name: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.spaceFacade.findByName(department, name)
  }

  @GraphQLField
  def spaces(ctx: AppContext[Unit]) = resolve {
    ctx.ctx.spaceFacade.findAll
  }

  @GraphQLField
  def searchSpaces(name: Option[String])(ctx: AppContext[Unit]) = resolve {
    name map ctx.ctx.spaceFacade.searchByName getOrElse Success(Nil)
  }

}
