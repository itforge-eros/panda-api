package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException.WrongUsernameOrPasswordException
import definitions.AppSecurity
import entities.{ExistingMember, MemberEntity}
import models.Member
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}
import persists.MemberPersist
import services.AuthenticationService

import scala.util.Try

class AuthenticationFacade(memberPersist: MemberPersist,
                           authenticationService: AuthenticationService) {

  def findById(id: UUID): Option[Member] =
    memberPersist.find(id) map Member.of

  def login(username: String, password: String): Try[(Member, String)] =
    authenticationService.login(username, password)
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

    JwtCirce.encode(claim, AppSecurity.key, AppSecurity.algorithm)
  }

}
