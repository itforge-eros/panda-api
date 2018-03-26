package context

import persists.{RequestPersist, SpacePersist}

case class BaseContext(space: SpacePersist,
                       request: RequestPersist)
