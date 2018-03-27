package schemas

import models.{Mutation, Query}
import persists.{MemberPersist, RequestPersist, SpacePersist}

case class BaseContext(space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       query: Query,
                       mutation: Mutation)
