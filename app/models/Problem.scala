package models

import java.time.Instant
import java.util.UUID

import entities.ProblemEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

case class Problem(id: UUID,
                   title: String,
                   body: String,
                   isRead: Boolean,
                   createdAt: Instant,
                   @GraphQLExclude spaceId: UUID) extends BaseModel {

  @GraphQLField
  def space(ctx: AppContext[Problem]): Space = resolve {
    ctx.ctx.spaceFacade.find(spaceId)
  }

}

object Problem {

  def of(entity: ProblemEntity): Problem = entity.to[Problem]()

}
