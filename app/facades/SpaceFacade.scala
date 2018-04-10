package facades

import java.io
import java.time.Instant
import java.util.UUID

import entities.SpaceEntity
import models.{Member, Request, Reservation, Space}
import persists.{RequestPersist, ReservationPersist, SpacePersist}
import schemas.inputs.SpaceInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Space] = {
    Try(spacePersist.find(id))
      .flatMap(_.toTry(SpaceNotFoundException))
      .map(Space.of)
  }

  def findAll: Try[List[Space]] = {
    Try(spacePersist.findAll map Space.of)
  }

  def requests(id: UUID)
              (implicit member: Member): Try[List[Request]] = {
    Try(requestPersist.findBySpaceId(id) map Request.of)
  }

  def reservations(id: UUID): Try[List[Reservation]] = {
    Try(reservationPersist.findBySpaceId(id) map Reservation.of)
  }

  def create(input: SpaceInput)
            (implicit member: Member): Try[Space] = {
    lazy val spaceEntity = SpaceEntity(
      UUID.randomUUID(),
      input.name,
      input.description,
      input.capacity,
      input.isAvailable,
      Instant.now()
    )

    Try(spacePersist.insert(spaceEntity))
      .flatMap(if (_) Success(spaceEntity) else Failure(CannotCreateSpaceException))
      .map(Space.of)
  }

}
