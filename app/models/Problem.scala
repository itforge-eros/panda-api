package models

import java.util.UUID

import entities.ProblemEntity
import henkan.convert.Syntax._
import sangria.macros.derive.GraphQLExclude

case class Problem(id: UUID,
                   title: String,
                   body: String,
                   isRead: Boolean,
                   @GraphQLExclude spaceId: UUID) extends BaseModel {

  def space(ctx: AppContext[Problem]) = resolve {
    ctx.ctx.spaceFacade.find(spaceId)
  }

}

object Problem {

  def of(entity: ProblemEntity): Problem = entity.to[Problem]()

}
