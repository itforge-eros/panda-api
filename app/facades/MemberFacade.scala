package facades

import java.util.UUID

import models.{Member, Request, Reservation, Review}
import persists.{MemberPersist, RequestPersist, ReservationPersist, ReviewPersist}
import definitions.exceptions.AppException._

import scala.language.postfixOps
import scala.util.{Failure, Try}

class MemberFacade(memberPersist: MemberPersist,
                   requestPersist: RequestPersist,
                   reviewPersist: ReviewPersist,
                   reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Member] = {
    Try(memberPersist.find(id))
      .flatMap(_.toTry(MemberNotFoundException))
      .map(Member.of)
  }

  def findByUsername(username: String): Try[Member] = {
    Try(memberPersist.findByUsername(username))
      .flatMap(_.toTry(MemberNotFoundException))
      .map(Member.of)
  }

  def requests(id: UUID)(implicit member: Member): Try[List[Request]] = {
    member.id == id match {
      case true => Try(requestPersist.findByClientId(member.id) map Request.of)
      case false => Failure(NoPermissionException)
    }
  }

  def reviews(id: UUID)(implicit member: Member): Try[List[Review]] = {
    member.id == id match {
      case true => Try(reviewPersist.findByReviewerId(member.id) map Review.of)
      case false => Failure(NoPermissionException)
    }
  }

  def reservations(id: UUID): Try[List[Reservation]] = {
    Try(reservationPersist.findByClientId(id) map Reservation.of)
  }

}
