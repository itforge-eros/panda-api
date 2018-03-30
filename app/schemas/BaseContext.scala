package schemas

import persists._
import schemas.roots.{Mutation, Query}

case class BaseContext(query: Query,
                       mutation: Mutation,
                       space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       approval: ApprovalPersist)
