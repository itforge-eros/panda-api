package schemas

import models.{Mutation, Query}
import persists.{MemberPersist, RequestPersist, SpacePersist}

case class BaseContext(query: Query,
                       mutation: Mutation,
                       space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist)
