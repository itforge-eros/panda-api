package facades

import java.util.UUID

import models.{Member, Request, Review}
import persists.{MemberPersist, RequestPersist, ReviewPersist}

import scala.language.postfixOps
import scala.util.Try

class MemberFacade(memberPersist: MemberPersist,
                   requestPersist: RequestPersist,
                   reviewPersist: ReviewPersist) extends BaseFacade {

  def find(id: UUID): Try[Member] = {
    lazy val maybeMember = memberPersist.find(id)

    TryWith(
      Guard(maybeMember.isEmpty, MemberNotFoundException)
    ) {
      maybeMember map Member.of get
    }
  }

  def findByUsername(username: String): Try[Member] = {
    lazy val maybeMember = memberPersist.findByUsername(username)

    TryWith(
      Guard(maybeMember.isEmpty, MemberNotFoundException)
    ) {
      maybeMember map Member.of get
    }
  }

  def requests(implicit member: Member): Try[List[Request]] = Try {
    requestPersist.findByClientId(member.id) map Request.of
  }

  def reviews(implicit member: Member): Try[List[Review]] = Try {
    reviewPersist.findByReviewerId(member.id) map Review.of
  }

}
