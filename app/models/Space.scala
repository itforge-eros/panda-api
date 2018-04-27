package models

import java.time.Instant
import java.util.UUID

import entities.SpaceEntity
import sangria.macros.derive._
import utils.datatypes.UuidUtil

import scala.concurrent.Future

case class Space(id: UUID,
                 name: String,
                 fullName: String,
                 description: Option[String],
                 tags: List[String],
                 capacity: Option[Int],
                 isAvailable: Boolean,
                 createdAt: Instant,
                 updatedAt: Instant,
                 @GraphQLExclude departmentId: UUID) extends BaseModel {

  @GraphQLField
  def images(ctx: AppContext[Space]): Future[List[String]] = {
    ctx.ctx.spaceFacade.images(id)
  }

  @GraphQLField
  def department(ctx: AppContext[Space]): Department = resolve {
    ctx.ctx.departmentFacade.find(departmentId)
  }

  @GraphQLField
  def requests(ctx: AppContext[Space]): List[Request] = authorize(ctx) { implicit identity =>
    ctx.ctx.spaceFacade.requests(this)
  }

  @GraphQLField
  def reservations(ctx: AppContext[Space]): List[Reservation] = resolve {
    ctx.ctx.spaceFacade.reservations(this)
  }

  @GraphQLField
  def problems(ctx: AppContext[Space]): List[Problem] = resolve {
    ctx.ctx.spaceFacade.problems(this)
  }

}

object Space {

  def of(entity: SpaceEntity): Space = Space(
    entity.id,
    entity.name,
    entity.fullName,
    entity.description,
    entity.tags,
    entity.capacity,
    entity.isAvailable,
    entity.createdAt,
    entity.updatedAt,
    entity.departmentId
  )

}
