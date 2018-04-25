package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.SpaceException._
import entities.SpaceEntity
import models._
import models.enums.Access.{SpaceDeleteAccess, SpaceUpdateAccess}
import models.enums.SpaceCategory
import models.inputs.{CreateSpaceInput, DeleteSpaceInput, UpdateSpaceInput}
import persists._
import utils.Guard

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(auth: AuthorizationFacade,
                  spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist,
                  departmentPersist: DepartmentPersist,
                  problemPersist: ProblemPersist,
                  searchPersist: SearchPersist) extends BaseFacade {

  def find(id: UUID): Try[Space] = validateWith() {
    spacePersist.find(id) toTry SpaceNotFoundException map Space.of
  }

  def findByName(department: String, name: String): Try[Space] = validateWith() {
    spacePersist.findByName(department, name) toTry SpaceNotFoundException map Space.of
  }

  def searchByName(name: String): Try[List[Space]] = validate() {
    spacePersist.searchByName(name) map Space.of
  }

  def search(query: String): Try[List[Space]] = validate() {
    val tokens = query.split(" ").toList flatMap { token =>
      token.split(":").toList match {
        case key :: value :: Nil => Some(SearchToken(key, value))
        case queryString :: Nil => Some(SearchToken("query", queryString))
        case _ => None
      }
    }
    val search = SearchStatement(tokens)

    searchPersist.space(
      search.query,
      search.department,
      search.tags,
      search.capacity
    ) map Space.of
  }

  def findAll: Try[List[Space]] = validate() {
    spacePersist.findAll map Space.of
  }

  def requests(space: Space)
              (implicit identity: Identity): Try[List[Request]] = {
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
      input.tags,
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
      Guard(!input.tags.forall(isSpaceTagValid), InvalidSpaceTag),
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
      input.tags,
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
      Guard(!input.tags.forall(isSpaceTagValid), InvalidSpaceTag),
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException),
      Guard(!auth.hasAccess(SpaceUpdateAccess)(resource.accesses), NoPermissionException),
      Guard(input.name != maybeSpaceEntity.get.name
        && spacePersist.findByDepartmentId(maybeSpaceEntity.get.departmentId).exists(input.name == _.name), SpaceNameAlreadyTaken)
    ) {
      spacePersist.update(updatedSpaceEntity) match {
        case true => Success(updatedSpaceEntity) map Space.of
        case false => Failure(CannotUpdateSpaceException)
      }
    }
  }

  def delete(input: DeleteSpaceInput)
            (implicit identity: Identity): Try[Department] = {
    lazy val resource = identity.department(departmentEntity.id).get
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)
    lazy val departmentEntity = departmentPersist.find(maybeSpaceEntity.get.departmentId).get
    lazy val requests = requestPersist.findBySpaceId(maybeSpaceEntity.get.id)
    lazy val reservations = requestPersist.findBySpaceId(maybeSpaceEntity.get.id)

    validateWith(
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException),
      Guard(!auth.hasAccess(SpaceDeleteAccess)(resource.accesses), NoPermissionException),
      Guard(requests.nonEmpty || reservations.nonEmpty, CannotDeleteSpaceException)
    ) {
      spacePersist.delete(input.spaceId) match {
        case true => Success(departmentEntity) map Department.of
        case false => Failure(CannotDeleteSpaceException)
      }
    }
  }


  private def isSpaceNameValid(name: String): Boolean = {
    raw"^[a-zA-Z0-9._-]+$$".r.findFirstIn(name).isDefined
  }

  private def isSpaceTagValid(tag: String): Boolean = {
    raw"^[a-z0-9-]+$$".r.findFirstIn(tag).isDefined
  }

  case class SearchToken(key: String, value: String)

  case class SearchStatement(query: String,
                             department: Option[String],
                             tags: List[String],
                             capacity: Option[Int])

  object SearchStatement {

    def apply(tokens: List[SearchToken]): SearchStatement = SearchStatement(
      tokens.filter(_.key == "query").map(_.value).mkString(" | "),
      tokens.find(_.key == "department").map(_.value),
      tokens.find(_.key == "tags").map(_.value.split(",").toList).getOrElse(Nil),
      tokens.find(_.key == "capacity").flatMap(_.value.toIntOption)
    )

  }

}
