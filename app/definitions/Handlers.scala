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
    case (_, e @ MaxQueryDepthReachedError(_)) => HandledException(e.getMessage)
    case (_, e: SafeException) => HandledException(e.getMessage)
  }

  lazy val onViolation: PartialFunction[(ResultMarshaller, Violation), HandledException] = {
    case (_, e @ InvalidIdViolation) => HandledException(e.errorMessage)
    case (_, e @ InvalidInstantViolation) => HandledException(e.errorMessage)
    case (_, e @ InvalidDateViolation) => HandledException(e.errorMessage)
    case (_, e @ InvalidRangeViolation) => HandledException(e.errorMessage)
  }

  def graphqlAction: PartialFunction[Throwable, Future[Result]] = {
    case e @ GraphqlVariablesParseError => response(Failure(e).badRequest)
    case e @ UnauthorizedException => response(Failure(e).unauthorized)
    case e: JwtDecodingException => response(Failure(e).badRequest)
    case e: BadRequestException => response(Failure(e).badRequest)
    case e: UnexpectedError => response(Failure(e).unauthorized)
    case e => response(Failure(new UnexpectedError(e)).internalServerError)
  }

  private def response(result: Result): Future[Result] =
    Future.successful(result)

}
