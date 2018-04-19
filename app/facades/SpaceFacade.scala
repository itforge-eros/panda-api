package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.SpaceException.{CannotCreateSpaceException, SpaceNameAlreadyTaken, SpaceNotFoundException}
import entities.SpaceEntity
import models.inputs.CreateSpaceInput
import models.{Member, Request, Reservation, Space}
import persists.{DepartmentPersist, RequestPersist, ReservationPersist, SpacePersist}

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist,
                  departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Space] = validateWith() {
    spacePersist.find(id) toTry SpaceNotFoundException map Space.of
  }

  def findByName(department: String, name: String): Try[Space] = validateWith() {
    spacePersist.findByName(department, name) toTry SpaceNotFoundException map Space.of
  }

  def searchByName(name: String): Try[List[Space]] = validate() {
    spacePersist.searchByName(name) map Space.of
  }

  def findAll: Try[List[Space]] = validate() {
    spacePersist.findAll map Space.of
  }

  def requests(id: UUID)
              (implicit member: Member): Try[List[Request]] = validate() {
    requestPersist.findBySpaceId(id) map Request.of
  }

  def reservations(id: UUID): Try[List[Reservation]] = validate() {
    reservationPersist.findBySpaceId(id) map Reservation.of
  }

  def create(input: CreateSpaceInput)
            (implicit member: Member): Try[Space] = {
    lazy val maybeDepartmentEntity = departmentPersist.find(input.departmentId)
    lazy val spaceEntity = SpaceEntity(
      UUID.randomUUID(),
      input.name,
      input.fullName,
      input.description,
      input.category.name,
      input.capacity,
      input.isAvailable,
      Instant.now(),
      input.departmentId
    )

    validateWith(
      Guard(maybeDepartmentEntity.isEmpty, DepartmentNotFoundException),
      Guard(spacePersist.findByName(maybeDepartmentEntity.get.name, input.name).isDefined, SpaceNameAlreadyTaken)
    ) {
      spacePersist.insert(spaceEntity) match {
        case true => Success(spaceEntity) map Space.of
        case false => Failure(CannotCreateSpaceException)
      }
    }
  }

}
