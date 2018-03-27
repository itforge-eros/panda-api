package context

import persists.{MemberPersist, RequestPersist, SpacePersist}
import schemas.SchemeDefinition.{Mutation, Query}

case class BaseContext(space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       query: Query,
                       mutation: Mutation)
