package schemas

import persists._
import schemas.mutations.Mutation
import schemas.queries.Query

case class PandaContext(query: Query,
                        mutation: Mutation,
                        spacePersist: SpacePersist,
                        requestPersist: RequestPersist,
                        memberPersist: MemberPersist,
                        reviewPersist: ReviewPersist,
                        reservationPersist: ReservationPersist)
