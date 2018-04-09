package definitions

import controllers.api.TryResults
import definitions.AppException._
import play.api.mvc.Result
import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}

import scala.concurrent.Future
import scala.util.Failure

object Handlers extends TryResults {

  lazy val exceptionHandler = ExceptionHandler {
    case (_, error @ TooComplexQueryError) ⇒ HandledException(error.getMessage)
    case (_, error @ MaxQueryDepthReachedError(_)) ⇒ HandledException(error.getMessage)
    case (_, error @ WrongUsernameOrPasswordException) ⇒ HandledException(error.getMessage)
    case (_, error @ UnauthorizedException) ⇒ HandledException(error.getMessage)
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
