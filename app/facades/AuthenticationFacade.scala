package facades

import java.time.Instant
import java.util.UUID

import definitions.AppSecurity
import definitions.exceptions.AppException._
import definitions.exceptions.InternalException.UnspecifiedSecretKeyException
import entities.{ExistingMemberEntity, MemberEntity}
import models.{Member, MemberWithToken}
import pdi.jwt.{JwtCirce, JwtClaim}
import persists.MemberPersist
import play.api.Configuration
import services.AuthenticationService

import scala.util.Try

class AuthenticationFacade(memberPersist: MemberPersist,
                           authenticationService: AuthenticationService,
                           configuration: Configuration) extends BaseFacade {

  def findById(id: UUID): Try[Member] = ValidateWith() {
    memberPersist.find(id)
      .toTry(MemberNotFoundException)
      .map(Member.of)
  }


  def login(username: String, password: String): Try[MemberWithToken] = ValidateWith() {
    authenticationService.login(username, password)
      .toTry(WrongUsernameOrPasswordException)
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

    JwtCirce.encode(claim, appSecretKey, AppSecurity.algorithm)
  }


  val appSecretKey = configuration.get[Option[String]]("play.http.secret.key") getOrElse (throw UnspecifiedSecretKeyException)

}
