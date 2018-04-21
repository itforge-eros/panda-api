package facades

import java.time.Instant
import java.util.UUID

import definitions.AppSecurity
import definitions.exceptions.AuthorizationException.WrongUsernameOrPasswordException
import definitions.exceptions.InternalException.UnspecifiedSecretKeyException
import definitions.exceptions.MemberException.MemberNotFoundException
import entities.{ExistingMemberEntity, MemberEntity}
import models.{Identity, Member, MemberWithToken}
import pdi.jwt.{JwtCirce, JwtClaim}
import persists.{AuthenticationPersist, MemberPersist}
import play.api.Configuration
import services.AuthenticationService

import scala.util.Try

class AuthenticationFacade(memberPersist: MemberPersist,
                           authenticationService: AuthenticationService,
                           authenticationPersist: AuthenticationPersist,
                           configuration: Configuration) extends BaseFacade {

  def findById(id: UUID): Try[Member] = validateWith() {
    memberPersist.find(id)
      .toTry(MemberNotFoundException)
      .map(Member.of)
  }

  def getIdentity(member: Member): Identity = {
    val relations = authenticationPersist.findByMemberId(member.id)

    new Identity(member, relations)
  }


  def login(username: String, password: String): Try[MemberWithToken] = validateWith() {
    authenticationService.login(username, password)
      .toTry(WrongUsernameOrPasswordException)
      .flatMap(findOrElseCreateMember)
      .map(Member.of)
      .map(member => MemberWithToken(member, createToken(member.id)))
  }


  private def findOrElseCreateMember(existingMember: ExistingMemberEntity): Try[MemberEntity] = Try {
    memberPersist.findByUsername(existingMember.username)
      .getOrElse(memberPersist.insert(existingMember.toMember).get)
  }

  private def createToken(memberId: UUID): String = {
    val claim = JwtClaim(
      subject = Some(memberId.toString),
      issuedAt = Some(Instant.now().getEpochSecond),
      expiration = Some(Instant.now().plusSeconds(31536000).getEpochSecond)
    )

    JwtCirce.encode(claim, appSecretKey, AppSecurity.algorithm)
  }


  val appSecretKey = configuration.get[Option[String]]("play.http.secret.key") getOrElse (throw UnspecifiedSecretKeyException)

}
