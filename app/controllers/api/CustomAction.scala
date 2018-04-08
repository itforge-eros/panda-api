package controllers.api

import definitions.AppException._
import definitions.AppSecurity
import facades.AuthenticationFacade
import models.Member
import pdi.jwt.{JwtCirce, JwtClaim}
import play.api.UnexpectedException
import play.api.libs.circe.Circe
import play.api.mvc._
import utils.Functional._
import utils.datatypes.UuidUtil.maybeUuid

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait CustomAction extends TryResults
  with FutureResults
  with Circe {

  protected def GraphqlAction = new ActionBuilder[GraphqlRequest, AnyContent] {
    override def parser = BodyParsers.utils.ignore(AnyContentAsEmpty: AnyContent)
    override protected def executionContext = ec
    override def invokeBlock[A](request: Request[A], block: GraphqlRequest[A] => Future[Result]) = {
      findMember(request) flatMapFuture { member =>
        block(GraphqlRequest(request, member))
      } recoverWith {
        case GraphqlVariablesParseError => response(Failure(GraphqlVariablesParseError).badRequest)
        case UnauthorizedException => response(Failure(UnauthorizedException).unauthorized)
        case e: BadRequestException => response(Failure(e).badRequest)
        case e: UnexpectedError => response(Failure(e).unauthorized)
      }
    }
  }


  private def findMember(request: Request[_]): Try[Option[Member]] = swapTryOption(
    getToken(request)
      .map(_.flatMap(findMemberFromToken))
  )

  private def getToken(request: Request[_]): Option[Try[String]] =
    request.headers.get(AUTHORIZATION_KEY) map { value =>
      value.split(" ").toList match {
        case "Bearer" :: token :: Nil => Success(token)
        case _ => Failure(new WrongBearerHeaderFormatException(value))
      }
    }

  private def findMemberFromToken(token: String): Try[Member] =
    JwtCirce.decode(token, AppSecurity.key, Seq(AppSecurity.algorithm))
      .flatMap(authorize(_).toTry(new UnexpectedError(MalformedJwtTokenException)))

  private def authorize(claim: JwtClaim): Option[Member] =
    claim.subject
      .flatMap(maybeUuid)
      .flatMap(authenticationFacade.findById)

  private def response(result: Result): Future[Result] =
    Future.successful(result)

  private val AUTHORIZATION_KEY = "Authorization"


  case class GraphqlRequest[A](request: Request[A],
                               member: Option[Member]) extends WrappedRequest[A](request)

  protected val authenticationFacade: AuthenticationFacade

  implicit protected val ec: ExecutionContext

}
