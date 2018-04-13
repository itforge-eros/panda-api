package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import entities.SpaceEntity
import models.{Member, Request, Reservation, Space}
import persists.{RequestPersist, ReservationPersist, SpacePersist}
import schemas.inputs.SpaceInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Space] = ValidateWith() {
    spacePersist.find(id) toTry SpaceNotFoundException map Space.of
  }

  def findByName(name: String): Try[List[Space]] = Validate() {
    spacePersist.findByName(name) map Space.of
  }

  def findAll: Try[List[Space]] = Validate() {
    spacePersist.findAll map Space.of
  }

  def requests(id: UUID)
              (implicit member: Member): Try[List[Request]] = Validate() {
    requestPersist.findBySpaceId(id) map Request.of
  }

  def reservations(id: UUID): Try[List[Reservation]] = Validate() {
    reservationPersist.findBySpaceId(id) map Reservation.of
  }

  def create(input: SpaceInput)
            (implicit member: Member): Try[Space] = ValidateWith() {
    lazy val spaceEntity = SpaceEntity(
      UUID.randomUUID(),
      input.name,
      input.description,
      input.capacity,
      input.isAvailable,
      Instant.now(),
      input.groupId
    )

    spacePersist.insert(spaceEntity) match {
      case true => Success(Space.of(spaceEntity))
      case false => Failure(CannotCreateSpaceException)
    }
  }

}
