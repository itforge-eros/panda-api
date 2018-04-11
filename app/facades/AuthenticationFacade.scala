package facades

import java.time.Instant
import java.util.UUID

import definitions.AppSecurity
import definitions.exceptions.AppException._
import entities.{ExistingMemberEntity, MemberEntity}
import models.{Member, MemberWithToken}
import pdi.jwt.{JwtCirce, JwtClaim}
import persists.MemberPersist
import services.AuthenticationService

import scala.util.Try

class AuthenticationFacade(memberPersist: MemberPersist,
                           authenticationService: AuthenticationService) extends BaseFacade {

  def findById(id: UUID): Try[Member] = {
    Try(memberPersist.find(id))
      .flatMap(_.toTry(MemberNotFoundException))
      .map(Member.of)
  }


  def login(username: String, password: String): Try[MemberWithToken] = {
    authenticationService.login(username, password)
      .flatMap(_.toTry(WrongUsernameOrPasswordException))
      .flatMap(findOrElseCreateMember)
      .map(Member.of)
      .map(member => MemberWithToken(member, createToken(member.id)))
  }


  private def findOrElseCreateMember(existingMember: ExistingMemberEntity): Try[MemberEntity] = Try {
    memberPersist.findByUsername(existingMember.username)
      .getOrElse(memberPersist.insert(MemberEntity.of(existingMember)).get)
  }

  private def createToken(memberId: UUID): String = {
    val claim = JwtClaim(
      subject = Some(memberId.toString),
      issuedAt = Some(Instant.now().getEpochSecond),
      expiration = Some(Instant.now().plusSeconds(31536000).getEpochSecond)
    )

    JwtCirce.encode(claim, AppSecurity.key, AppSecurity.algorithm)
  }

}
