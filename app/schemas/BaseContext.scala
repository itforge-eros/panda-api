package schemas

import persists._
import schemas.roots.{Mutation, Query}

case class BaseContext(query: Query,
                       mutation: Mutation,
                       spacePersist: SpacePersist,
                       requestPersist: RequestPersist,
                       memberPersist: MemberPersist,
                       reviewPersist: ReviewPersist)
