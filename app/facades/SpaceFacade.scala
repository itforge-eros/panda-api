package facades

import java.time.Instant
import java.util.{Date, UUID}

import akka.actor.ActorSystem
import clients.ImageClient
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.SpaceException._
import entities.SpaceEntity
import models._
import models.enums.Access.{SpaceCreateAccess, SpaceDeleteAccess, SpaceImageUploadAccess, SpaceUpdateAccess}
import models.inputs.{CreateSpaceInput, DeleteSpaceInput, UpdateSpaceInput, UploadSpaceImageInput}
import persists._
import utils.Guard
import utils.datatypes.{DateUtil, UuidUtil}
import utils.datatypes.UuidUtil.uuidToBase62

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class SpaceFacade(auth: AuthorizationFacade,
                  imageUploadFacade: ImageUploadFacade,
                  spacePersist: SpacePersist,
                  requestPersist: RequestPersist,
                  reservationPersist: ReservationPersist,
                  departmentPersist: DepartmentPersist,
                  problemPersist: ProblemPersist,
                  searchPersist: SearchPersist,
                  imageClient: ImageClient,
                  actorSystem: ActorSystem) extends BaseFacade {

  def find(id: UUID): Try[Space] = validateWith() {
    spacePersist.find(id) toTry SpaceNotFoundException map Space.of
  }

  def findByName(department: String, name: String): Try[Space] = validateWith() {
    spacePersist.findByName(department, name) toTry SpaceNotFoundException map Space.of
  }

  def searchByName(name: String): Try[List[Space]] = validate() {
    spacePersist.searchByName(name) map Space.of
  }

  def search(query: String): Try[List[Space]] = validateWith() {
    val tokens = query.split(" ").toList flatMap { token =>
      token.split(":").toList match {
        case key :: value :: Nil => Some(SearchToken(key, value))
        case queryString :: Nil => Some(SearchToken("query", queryString))
        case _ => None
      }
    }

    val search = SearchStatement(tokens)
    val invalidPrefixes = tokens
      .map(_.prefix)
      .filterNot(SearchStatement.validPrefix contains _)
    lazy val searchResult = {
      searchPersist.space(
        search.query,
        search.department,
        search.tags,
        search.capacity,
        search.date
      ) map Space.of
    }

    invalidPrefixes.isEmpty match {
      case true => Success(searchResult)
      case false => Failure(new InvalidSearchPrefix(invalidPrefixes))
    }


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

  def images(id: UUID): Future[List[String]] = {
    implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

    imageClient.hasImage(s"itforge/panda/images/spaces/${UuidUtil.uuidToBase62(id)}/1.jpg") map {
      case true => s"https://storage.googleapis.com/itforge/panda/images/spaces/${UuidUtil.uuidToBase62(id)}/1.jpg" :: Nil
      case false => Nil
    }
  }

  def create(input: CreateSpaceInput)
            (implicit identity: Identity): Try[Space] = {
    lazy val accesses = identity.accesses(maybeDepartmentEntity.get.id)
    lazy val maybeDepartmentEntity = departmentPersist.find(input.departmentId)
    lazy val spaceEntity = SpaceEntity(
      UUID.randomUUID(),
      input.name,
      input.fullName,
      input.description,
      input.tags,
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
      Guard(!auth.hasAccess(SpaceCreateAccess)(accesses), NoPermissionException),
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
    lazy val accesses = identity.accesses(maybeSpaceEntity.get.departmentId)
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)
    lazy val updatedSpaceEntity = SpaceEntity(
      input.spaceId,
      input.name,
      input.fullName,
      input.description,
      input.tags,
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
      Guard(!auth.hasAccess(SpaceUpdateAccess)(accesses), NoPermissionException),
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
    lazy val accesses = identity.accesses(departmentEntity.id)
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)
    lazy val departmentEntity = departmentPersist.find(maybeSpaceEntity.get.departmentId).get
    lazy val requests = requestPersist.findBySpaceId(maybeSpaceEntity.get.id)
    lazy val reservations = requestPersist.findBySpaceId(maybeSpaceEntity.get.id)
    lazy val problems = problemPersist.findBySpaceId(maybeSpaceEntity.get.id)

    validateWith(
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException),
      Guard(!auth.hasAccess(SpaceDeleteAccess)(accesses), NoPermissionException),
      Guard(requests.nonEmpty || reservations.nonEmpty || problems.nonEmpty, CannotDeleteSpaceException)
    ) {
      spacePersist.delete(input.spaceId) match {
        case true => Success(departmentEntity) map Department.of
        case false => Failure(CannotDeleteSpaceException)
      }
    }
  }

  def uploadImage(input: UploadSpaceImageInput)
                 (implicit identity: Identity): Try[String] = {
    lazy val accesses = identity.accesses(maybeSpaceEntity.get.departmentId)
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)

    validate(
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException),
      Guard(!auth.hasAccess(SpaceImageUploadAccess)(accesses), NoPermissionException)
    ) {
      imageUploadFacade.createSpaceUploadSignUrl(uuidToBase62(input.spaceId))
    }
  }


  private def isSpaceNameValid(name: String): Boolean = {
    raw"^[a-zA-Z0-9._-]+$$".r.findFirstIn(name).isDefined
  }

  private def isSpaceTagValid(tag: String): Boolean = {
    raw"^[a-z0-9-]+$$".r.findFirstIn(tag).isDefined
  }

  case class SearchToken(prefix: String, value: String)

  case class SearchStatement(query: String,
                             department: Option[String],
                             tags: List[String],
                             capacity: Option[Int],
                             date: Date)

  object SearchStatement {

    def apply(tokens: List[SearchToken]): SearchStatement = SearchStatement(
      tokens.filter(_.prefix == "query")
        .map(_.value)
        .mkString(" | "),
      tokens.find(_.prefix == "department")
        .map(_.value),
      tokens.find(_.prefix == "tags")
        .map(_.value.split(",").toList)
        .getOrElse(Nil),
      tokens.find(_.prefix == "capacity")
        .flatMap(_.value.toIntOption),
      tokens.find(_.prefix == "date")
        .map(_.value)
        .flatMap(DateUtil.parseDate)
        .getOrElse(Date.from(Instant.now()))
    )

    val validPrefix = List("query", "department", "tags", "capacity", "date")

  }

}
