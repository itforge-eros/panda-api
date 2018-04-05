package facades

import java.time.Instant
import java.util.UUID

import definitions.AppException.WrongUsernameOrPasswordException
import entities.{ExistingMember, MemberEntity}
import models.Member
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}
import persists.MemberPersist
import services.AuthService

import scala.util.Try

class AuthFacade(memberPersist: MemberPersist, authService: AuthService) {

  def authenticate(username: String, password: String): Try[(Member, String)] =
    authService.login(username, password)
      .toRight(WrongUsernameOrPasswordException)
      .map(findOrElseCreateMember)
      .map(Member.of)
      .map(member => (member, createToken(member.id)))
      .toTry


  private def findOrElseCreateMember(existingMember: ExistingMember): MemberEntity =
    memberPersist.findByUsername(existingMember.username)
     .getOrElse(memberPersist.insert(MemberEntity.of(existingMember)).get)

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
