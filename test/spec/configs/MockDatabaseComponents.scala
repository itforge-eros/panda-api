package spec.configs

import com.softwaremill.macwire.wire
import org.scalamock.scalatest.MockFactory
import persists._
import spec.mockpersists._

trait MockDatabaseComponents extends MockFactory {

  lazy val spacePersist: SpacePersist = wire[MockSpacePersist]
  lazy val requestPersist: RequestPersist = mock[RequestPersist]
  lazy val memberPersist: MemberPersist = mock[MemberPersist]
  lazy val reviewPersist: ReviewPersist = mock[ReviewPersist]
  lazy val reservationPersist: ReservationPersist = mock[ReservationPersist]

}
