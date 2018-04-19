package controllers.api

import definitions.exceptions.AuthorizationException.{JwtDecodingException, MalformedJwtTokenException, WrongBearerHeaderFormatException}
import definitions.exceptions.HttpException.UnexpectedError
import definitions.{AppSecurity, Handlers}
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
    override def parser: BodyParser[AnyContent] = BodyParsers.utils.ignore(AnyContentAsEmpty: AnyContent)
    override protected def executionContext: ExecutionContext = ec
    override def invokeBlock[A](request: Request[A], block: GraphqlRequest[A] => Future[Result]): Future[Result] = {
      findMember(request) flatMapFuture { member =>
        block(GraphqlRequest(request, member))
      } recoverWith Handlers.graphqlAction
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

  private def findMemberFromToken(token: String): Try[Member] =  {
    JwtCirce.decode(token, authenticationFacade.appSecretKey, Seq(AppSecurity.algorithm))
      .recoverWith { case e => Failure(new JwtDecodingException(e.getMessage)) }
      .flatMap(authorize(_).toTry(new UnexpectedError(MalformedJwtTokenException)))
  }

  private def authorize(claim: JwtClaim): Option[Member] = {
    claim.subject
      .flatMap(maybeUuid)
      .flatMap(authenticationFacade.findById(_).toOption)
  }

  private def response(result: Result): Future[Result] =
    Future.successful(result)

  private val AUTHORIZATION_KEY = "Authorization"

  private implicit class CustomResult (result: Result) {
    def enableCors =  result.withHeaders(
      "Access-Control-Allow-Origin" -> "localhost:9000",
      "Access-Control-Allow-Methods" -> "OPTIONS, GET, POST",
      "Access-Control-Allow-Headers" -> "Accept, Content-Type, Origin, Authorization",
      "Access-Control-Allow-Credentials" -> "true"
    )
  }


  case class GraphqlRequest[A](request: Request[A],
                               viewer: Option[Member]) extends WrappedRequest[A](request)

  protected val authenticationFacade: AuthenticationFacade

  implicit protected val ec: ExecutionContext

}
