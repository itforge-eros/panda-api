package controllers.api

import definitions.AppException.{GraphqlVariablesParseError, UnauthorizedException}
import definitions.AppSecurity
import facades.AuthenticationFacade
import models.Member
import pdi.jwt.{JwtCirce, JwtClaim}
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
      findMember(request).toFuture flatMap { member =>
        block(GraphqlRequest(request, member))
      } recoverWith {
        case GraphqlVariablesParseError => response(Failure(GraphqlVariablesParseError).badRequest)
        case UnauthorizedException => response(Failure(UnauthorizedException).unauthorized)
      }
    }
  }


  private def response(result: Result): Future[Result] =
    Future.successful(result)

  private def getToken(request: Request[_]): Option[JwtClaim] =
    request.headers.get(AUTHORIZATION_KEY)
      .flatMap(_.split(" ").lift(1))
      .flatMap(JwtCirce.decode(_, AppSecurity.key, Seq(AppSecurity.algorithm)).toOption)

  private def findMember(request: Request[_]): Try[Member] =
    getToken(request)
      .flatMap(_.subject)
      .flatMap(maybeUuid)
      .flatMap(authentication.findById)
      .fold[Try[Member]](Failure(UnauthorizedException))(Success(_))

  private val AUTHORIZATION_KEY = "Authorization"


  case class GraphqlRequest[A](request: Request[A],
                               member: Member) extends WrappedRequest[A](request)

  protected val authentication: AuthenticationFacade

  implicit protected val ec: ExecutionContext

}
