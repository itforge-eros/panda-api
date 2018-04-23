package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.SpaceException._
import entities.SpaceEntity
import models.inputs.{CreateSpaceInput, UpdateSpaceInput}
import models._
import models.enums.Access.{RoleUpdateAccess, SpaceUpdateAccess}
import models.enums.SpaceCategory
import persists._
import utils.Guard

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(auth: AuthorizationFacade,
                  spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist,
                  departmentPersist: DepartmentPersist,
                  problemPersist: ProblemPersist) extends BaseFacade {

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

  def requests(space: Space)
              (implicit identity: Identity): Try[List[Request]] = {
    lazy val maybeDepartmentEntity = departmentPersist.find(space.departmentId)

    validate() {
      requestPersist.findBySpaceId(space.id) map Request.of
    }
  }

  def reservations(space: Space): Try[List[Reservation]] = validate() {
    reservationPersist.findBySpaceId(space.id) map Reservation.of
  }

  def problems(space: Space): Try[List[Problem]] = validate() {
    problemPersist.findBySpaceId(space.id) map Problem.of
  }

  def create(input: CreateSpaceInput)
            (implicit identity: Identity): Try[Space] = {
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
      Instant.now(),
      input.departmentId
    )

    validateWith(
      Guard(!isSpaceNameValid(input.name), InvalidSpaceNameException),
      Guard(input.fullName.isEmpty, InvalidSpaceFullNameException),
      Guard(SpaceCategory(input.category.name).isEmpty, SpaceCategoryNotFoundException),
      Guard(maybeDepartmentEntity.isEmpty, DepartmentNotFoundException),
      Guard(spacePersist.findByName(maybeDepartmentEntity.get.name, input.name).isDefined, SpaceNameAlreadyTaken)
    ) {
      spacePersist.insert(spaceEntity) match {
        case true => Success(spaceEntity) map Space.of
        case false => Failure(CannotCreateSpaceException)
      }
    }
  }

  def update(input: UpdateSpaceInput)
            (implicit identity: Identity): Try[Space] = {
    lazy val resource = identity.department(maybeSpaceEntity.get.departmentId).get
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)
    lazy val updatedSpaceEntity = SpaceEntity(
      input.spaceId,
      input.name,
      input.fullName,
      input.description,
      input.category.name,
      input.capacity,
      input.isAvailable,
      maybeSpaceEntity.get.createdAt,
      Instant.now(),
      maybeSpaceEntity.get.departmentId
    )

    validateWith(
      Guard(!isSpaceNameValid(input.name), InvalidSpaceNameException),
      Guard(input.fullName.isEmpty, InvalidSpaceFullNameException),
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException),
      Guard(!auth.hasAccess(SpaceUpdateAccess)(resource.accesses), NoPermissionException)
    ) {
      spacePersist.update(updatedSpaceEntity) match {
        case true => Success(updatedSpaceEntity) map Space.of
        case false => Failure(CannotUpdateSpaceException)
      }
    }
  }


  private def isSpaceNameValid(name: String): Boolean = {
    raw"^[a-zA-Z0-9._-]+$$".r.findFirstIn(name).isDefined
  }

}
