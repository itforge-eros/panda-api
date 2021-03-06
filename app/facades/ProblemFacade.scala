package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.HttpException.UnexpectedError
import definitions.exceptions.ProblemException.{CannotCreateProblemException, ProblemNotFoundException}
import definitions.exceptions.RoleException.CannotUpdateRoleException
import definitions.exceptions.SpaceException.SpaceNotFoundException
import entities.ProblemEntity
import models.enums.Access.{ProblemReadAccess, ProblemUpdateAccess}
import models.inputs.{CreateProblemInput, UpdateProblemInput}
import models.{Identity, Member, Problem}
import persists.{ProblemPersist, SpacePersist}
import utils.Guard

import scala.util.{Failure, Success, Try}

class ProblemFacade(auth: AuthorizationFacade,
                    spacePersist: SpacePersist,
                    problemPersist: ProblemPersist) extends BaseFacade {

  def find(id: UUID)
          (implicit identity: Identity): Try[Problem] = {
    lazy val accesses = identity.accesses(maybeSpaceEntity.get.departmentId)
    lazy val maybeProblemEntity = problemPersist.find(id)
    lazy val maybeSpaceEntity = maybeProblemEntity
      .map(_.spaceId)
      .flatMap(spacePersist.find)
      .toTry(SpaceNotFoundException)

    validate(
      Guard(maybeProblemEntity.isEmpty, ProblemNotFoundException),
      Guard(maybeSpaceEntity.isFailure, new UnexpectedError(maybeSpaceEntity.failed.get)),
      Guard(!auth.hasAccess(ProblemReadAccess)(accesses), NoPermissionException)
    ) {
      problemPersist.find(id) map Problem.of get
    }
  }

  def create(input: CreateProblemInput): Try[Problem] = {
    lazy val maybeSpaceEntity = spacePersist.find(input.spaceId)
    lazy val problemEntity = ProblemEntity(
      UUID.randomUUID(),
      input.title,
      input.body,
      isRead = false,
      Instant.now(),
      input.spaceId
    )

    validateWith(
      Guard(maybeSpaceEntity.isEmpty, SpaceNotFoundException)
    ) {
      problemPersist.create(problemEntity) match {
        case true => Success(problemEntity) map Problem.of
        case false => Failure(CannotCreateProblemException)
      }
    }
  }

  def update(input: UpdateProblemInput)
            (implicit identity: Identity): Try[Problem] = {
    lazy val accesses = identity.accesses(maybeSpaceEntity.get.departmentId)
    lazy val maybeProblemEntity = problemPersist.find(input.problemId)
    lazy val maybeSpaceEntity = maybeProblemEntity
      .map(_.spaceId)
      .flatMap(spacePersist.find)
      .toTry(SpaceNotFoundException)
    lazy val updatedSpaceEntity = maybeProblemEntity.get.copy(isRead = input.isRead)

    validateWith(
      Guard(maybeProblemEntity.isEmpty, ProblemNotFoundException),
      Guard(!auth.hasAccess(ProblemUpdateAccess)(accesses), NoPermissionException)
    ) {
      problemPersist.update(updatedSpaceEntity) match {
        case true => Success(updatedSpaceEntity) map Problem.of
        case false => Failure(CannotUpdateRoleException)
      }
    }
  }

}
