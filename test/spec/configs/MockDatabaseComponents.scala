package spec.configs

import com.softwaremill.macwire.wire
import persists.{MemberPersist, RequestPersist, ReviewPersist, SpacePersist}
import spec.mockpersists.{MockMemberPersist, MockRequestPersist, MockReviewPersist, MockSpacePersist}

trait MockDatabaseComponents {

  lazy val spacePersist: SpacePersist = wire[MockSpacePersist]
  lazy val requestPersist: RequestPersist = wire[MockRequestPersist]
  lazy val memberPersist: MemberPersist = wire[MockMemberPersist]
  lazy val reviewPersist: ReviewPersist = wire[MockReviewPersist]

}
