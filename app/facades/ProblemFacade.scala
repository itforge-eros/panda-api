package facades

import java.util.UUID

import definitions.exceptions.AuthorizationException
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.HttpException.UnexpectedError
import definitions.exceptions.ProblemException.{CannotCreateProblemException, ProblemNotFoundException}
import definitions.exceptions.SpaceException.SpaceNotFoundException
import entities.ProblemEntity
import models.inputs.CreateProblemInput
import models.{Member, Problem}
import persists.{ProblemPersist, SpacePersist}
import utils.Guard

import scala.util.{Failure, Success, Try}

class ProblemFacade(auth: AuthorizationFacade,
                    spacePersist: SpacePersist,
                    problemPersist: ProblemPersist) extends BaseFacade {

  def find(id: UUID)
          (implicit viewer: Member): Try[Problem] = {
    lazy val accesses = auth.accesses(viewer.id, maybeSpaceEntity.get.departmentId)
    lazy val maybeProblemEntity = problemPersist.find(id)
    lazy val maybeSpaceEntity = maybeProblemEntity
      .map(_.spaceId)
      .flatMap(spacePersist.find)
      .toTry(SpaceNotFoundException)

    validate(
      Guard(maybeProblemEntity.isEmpty, ProblemNotFoundException),
      Guard(maybeSpaceEntity.isFailure, new UnexpectedError(maybeSpaceEntity.failed.get)),
      Guard(!auth.canReadProblem(accesses.get), NoPermissionException)
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
      isRead = true,
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

}
