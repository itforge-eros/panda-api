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

  def find(id: UUID): Try[Member] = ValidateWith() {
    memberPersist.find(id) toTry MemberNotFoundException map Member.of
  }

  def findByUsername(username: String): Try[Member] = ValidateWith() {
    memberPersist.findByUsername(username) toTry MemberNotFoundException map Member.of
  }

  def requests(id: UUID)(implicit member: Member): Try[List[Request]] = Validate(
    Guard(member.id == id, NoPermissionException)
  ) {
    requestPersist.findByClientId(member.id) map Request.of
  }

  def reviews(id: UUID)(implicit member: Member): Try[List[Review]] = Validate(
    Guard(member.id == id, NoPermissionException)
  ) {
    reviewPersist.findByReviewerId(member.id) map Review.of
  }

  def reservations(id: UUID): Try[List[Reservation]] = Validate() {
    reservationPersist.findByClientId(id) map Reservation.of
  }

}
