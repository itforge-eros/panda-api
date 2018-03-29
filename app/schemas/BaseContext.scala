package schemas

import persists._

case class BaseContext(query: Query,
                       mutation: Mutation,
                       space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       approval: ApprovalPersist)
