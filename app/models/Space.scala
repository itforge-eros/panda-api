package models

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import entities.SpaceEntity
import henkan.convert.Syntax._
import sangria.macros.derive._

import scala.util.{Failure, Try}

case class Space(id: UUID,
                 name: String,
                 fullName: String,
                 description: Option[String],
                 capacity: Option[Int],
                 isAvailable: Boolean,
                 createdAt: Instant,
                 @GraphQLExclude departmentId: UUID) extends BaseModel {

  @GraphQLField
  def department(ctx: AppContext[Space]): Department = resolve {
    ctx.ctx.departmentFacade.find(departmentId)
  }

  @GraphQLField
  def requests(ctx: AppContext[Space]): List[Request] = authorize(ctx) { implicit member =>
    ctx.ctx.spaceFacade.requests(id)
  }

  @GraphQLField
  def reservations(ctx: AppContext[Space]): List[Reservation] = resolve {
    ctx.ctx.spaceFacade.reservations(id)
  }

}

object Space {

  def of(spaceEntity: SpaceEntity): Space = spaceEntity.to[Space]()

}
