package spec.configs

import persists.{MemberPersist, RequestPersist, SpacePersist}
import spec.persists.{MockMemberPersist, MockRequestPersist, MockSpacePersist}

trait MockDatabaseModule {

  lazy val spacePersist: SpacePersist = new MockSpacePersist
  lazy val requestPersist: RequestPersist = new MockRequestPersist
  lazy val memberPersist: MemberPersist = new MockMemberPersist

}
