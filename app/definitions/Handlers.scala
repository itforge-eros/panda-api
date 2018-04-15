package definitions

import controllers.api.TryResults
import definitions.Violations._
import definitions.exceptions.AppException._
import definitions.exceptions.AuthorizationException.{JwtDecodingException, UnauthorizedException}
import definitions.exceptions.GraphqlException.GraphqlVariablesParseError
import definitions.exceptions.HttpException.{BadRequestException, UnexpectedError}
import play.api.mvc.Result
import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}
import sangria.marshalling.ResultMarshaller
import sangria.validation.Violation

import scala.concurrent.Future
import scala.util.Failure

object Handlers extends TryResults {

  lazy val exceptionHandler = ExceptionHandler(onException, onViolation)

  lazy val onException: PartialFunction[(ResultMarshaller, Throwable), HandledException] = {
    case (_, error @ MaxQueryDepthReachedError(_)) => HandledException(error.getMessage)
    case (_, error: SafeException) => HandledException(error.getMessage)
  }

  lazy val onViolation: PartialFunction[(ResultMarshaller, Violation), HandledException] = {
    case (_, error @ InvalidUuidViolation) => HandledException(error.errorMessage)
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
