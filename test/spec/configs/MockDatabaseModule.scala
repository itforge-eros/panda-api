package spec.configs

import persists.{ReviewPersist, MemberPersist, RequestPersist, SpacePersist}
import spec.mockpersists.{MockReviewPersist, MockMemberPersist, MockRequestPersist, MockSpacePersist}

trait MockDatabaseModule {

  lazy val spacePersist: SpacePersist = new MockSpacePersist
  lazy val requestPersist: RequestPersist = new MockRequestPersist
  lazy val memberPersist: MemberPersist = new MockMemberPersist
  lazy val reviewPersist: ReviewPersist = new MockReviewPersist

}
