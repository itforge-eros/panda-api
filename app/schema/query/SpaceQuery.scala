package schema.query

import models.{BaseModel, Space}
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait SpaceQuery extends BaseModel {

  @GraphQLField
  def space(department: String, name: String)(ctx: AppContext[Unit]): Option[Space] = resolveOption {
    ctx.ctx.spaceFacade.findByName(department, name)
  }

  @GraphQLField
  def spaces(ctx: AppContext[Unit]): List[Space] = resolve {
    ctx.ctx.spaceFacade.findAll
  }

  @GraphQLField
  def searchSpaces(name: Option[String])(ctx: AppContext[Unit]): List[Space] = resolve {
    name map ctx.ctx.spaceFacade.searchByName getOrElse Success(Nil)
  }

}
