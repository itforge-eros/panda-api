package facades

import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.MemberException.MemberNotFoundException
import models._
import persists._
import utils.Guard

import scala.language.postfixOps
import scala.util.Try

class MemberFacade(memberPersist: MemberPersist,
                   requestPersist: RequestPersist,
                   reviewPersist: ReviewPersist,
                   reservationPersist: ReservationPersist,
                   rolePersist: RolePersist,
                   departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Member] = validateWith() {
    memberPersist.find(id) toTry MemberNotFoundException map Member.of
  }

  def findByUsername(username: String): Try[Member] = validateWith() {
    memberPersist.findByUsername(username) toTry MemberNotFoundException map Member.of
  }

  def requests(id: UUID)(implicit viewer: Member): Try[List[Request]] = validate(
    Guard(viewer.id != id, NoPermissionException)
  ) {
    requestPersist.findByClientId(viewer.id) map Request.of
  }

  def reviews(id: UUID)(implicit viewer: Member): Try[List[Review]] = validate(
    Guard(viewer.id != id, NoPermissionException)
  ) {
    reviewPersist.findByReviewerId(viewer.id) map Review.of
  }

  def reservations(id: UUID): Try[List[Reservation]] = validate() {
    reservationPersist.findByClientId(id) map Reservation.of
  }

  def roles(id: UUID): Try[List[Role]] = validate() {
    rolePersist.findByMemberId(id) map Role.of
  }

  def departments(id: UUID): Try[List[Department]] = validate() {
    departmentPersist.findByMemberId(id) map Department.of
  }

}
