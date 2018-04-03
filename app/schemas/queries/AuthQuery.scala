package schemas.queries

import java.time.Instant
import java.util.UUID

import models.{Member, MemberWithToken}
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps

trait AuthQuery {

  @GraphQLField
  def login(username: String, password: String)
           (implicit ctx: AppContext[Unit]): Option[MemberWithToken] =
    authenticate(username, password) map MemberWithToken.tupled

  private def authenticate(username: String, password: String)
                          (implicit ctx: AppContext[Unit]): Option[(Member, String)] = {
    lazy val isVerified = ctx.ctx.authService.verify(username, password)
    lazy val maybeMember = ctx.ctx.memberPersist.findByUsername(username) map Member.of
    lazy val maybeToken = maybeMember map (_.id) map createToken

    isVerified match {
      case true => for {
        member <- maybeMember
        token <- maybeToken
      } yield (member, token)
      case false => None
    }
  }

  private def createToken(memberId: UUID): String = {
    val claim = JwtClaim(
      subject = Some(memberId.toString),
      issuedAt = Some(Instant.now().getEpochSecond),
      expiration = Some(Instant.now().plusSeconds(31536000).getEpochSecond)
    )

    JwtCirce.encode(claim, key, algorithm)
  }

  private val key = "application-key"
  private val algorithm = JwtAlgorithm.HS256

}
