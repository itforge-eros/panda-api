package spec.configs

import persists.{ApprovalPersist, MemberPersist, RequestPersist, SpacePersist}
import spec.mockpersists.{MockApprovalPersist, MockMemberPersist, MockRequestPersist, MockSpacePersist}

trait MockDatabaseModule {

  lazy val spacePersist: SpacePersist = new MockSpacePersist
  lazy val requestPersist: RequestPersist = new MockRequestPersist
  lazy val memberPersist: MemberPersist = new MockMemberPersist
  lazy val approvalPersist: ApprovalPersist = new MockApprovalPersist

}
