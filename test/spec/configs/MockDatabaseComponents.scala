package spec.configs

import com.softwaremill.macwire.wire
import persists._
import spec.mockpersists._

trait MockDatabaseComponents {

  lazy val spacePersist: SpacePersist = wire[MockSpacePersist]
  lazy val requestPersist: RequestPersist = wire[MockRequestPersist]
  lazy val memberPersist: MemberPersist = wire[MockMemberPersist]
  lazy val reviewPersist: ReviewPersist = wire[MockReviewPersist]
  lazy val reservationPersist: ReservationPersist = wire[MockReservationPersist]

}
