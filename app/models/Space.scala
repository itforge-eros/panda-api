package models

import java.time.Instant
import java.util.UUID

import entities.SpaceEntity
import henkan.convert.Syntax._
import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Option[Int],
                 isAvailable: Boolean,
                 createdAt: Instant) {

  @GraphQLField
  def requests(ctx: AppContext[Space]): List[Request] = ctx.ctx.requestPersist.findBySpaceId(id) map Request.of

}

object Space {

  def of(spaceEntity: SpaceEntity): Space = spaceEntity.to[Space]()

}
