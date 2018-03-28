package schemas

import models.{Mutation, Query}
import persists._

case class BaseContext(query: Query,
                       mutation: Mutation,
                       space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       approval: ApprovalPersist)
