package models

import java.time.Instant
import java.util.UUID

import entities.SpaceEntity
import henkan.convert.Syntax._
import sangria.macros.derive._

import scala.util.Try

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Option[Int],
                 isAvailable: Boolean,
                 createdAt: Instant) extends BaseModel {

  @GraphQLField
  def requests(ctx: AppContext[Space]): Try[List[Request]] =
    authorize(ctx) { implicit member =>
      ctx.ctx.spaceFacade.incomingRequests(id)
    }

}

object Space {

  def of(spaceEntity: SpaceEntity): Space = spaceEntity.to[Space]()

}
