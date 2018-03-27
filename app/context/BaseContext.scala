package context

import persists.{MemberPersist, RequestPersist, SpacePersist}
import schemas.SchemeDefinition.Mutation

case class BaseContext(space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist,
                       mutation: Mutation)
