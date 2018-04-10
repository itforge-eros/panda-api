package definitions

import controllers.api.TryResults
import definitions.exceptions.AppException._
import definitions.Violations._
import play.api.mvc.Result
import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}
import sangria.marshalling.ResultMarshaller
import sangria.validation.Violation

import scala.concurrent.Future
import scala.util.Failure

object Handlers extends TryResults {

  lazy val exceptionHandler = ExceptionHandler(onException, onViolation)

  lazy val onException: PartialFunction[(ResultMarshaller, Throwable), HandledException] = {
    case (_, error @ TooComplexQueryError) => HandledException(error.getMessage)
    case (_, error @ MaxQueryDepthReachedError(_)) => HandledException(error.getMessage)

    case (_, error @ WrongUsernameOrPasswordException) => HandledException(error.getMessage)
    case (_, error @ UnauthorizedException) => HandledException(error.getMessage)
    case (_, error @ NoPermissionException) => HandledException(error.getMessage)
    case (_, error: AccessDeniedException) => HandledException(error.getMessage)

    case (_, error @ SpaceNotFoundException) => HandledException(error.getMessage)

    case (_, error @ MemberNotFoundException) => HandledException(error.getMessage)
    case (_, error @ CannotCreateSpaceException) => HandledException(error.getMessage)

    case (_, error @ RequestNotFoundException) => HandledException(error.getMessage)
    case (_, error @ CannotCreateRequestException) => HandledException(error.getMessage)
  }

  lazy val onViolation: PartialFunction[(ResultMarshaller, Violation), HandledException] = {
    case (_, error @ InvalidIdViolation) => HandledException(error.errorMessage)
    case (_, error @ InvalidInstantViolation) => HandledException(error.errorMessage)
    case (_, error @ InvalidDateViolation) => HandledException(error.errorMessage)
    case (_, error @ InvalidRangeViolation) => HandledException(error.errorMessage)
  }

  def graphqlAction: PartialFunction[Throwable, Future[Result]] = {
    case GraphqlVariablesParseError => response(Failure(GraphqlVariablesParseError).badRequest)
    case e: JwtDecodingException => response(Failure(e).badRequest)
    case UnauthorizedException => response(Failure(UnauthorizedException).unauthorized)
    case e: BadRequestException => response(Failure(e).badRequest)
    case e: UnexpectedError => response(Failure(e).unauthorized)
    case e => response(Failure(new UnexpectedError(e)).internalServerError)
  }

  private def response(result: Result): Future[Result] =
    Future.successful(result)

}
