package context

import persists.{MemberPersist, RequestPersist, SpacePersist}

case class BaseContext(space: SpacePersist,
                       request: RequestPersist,
                       member: MemberPersist)
