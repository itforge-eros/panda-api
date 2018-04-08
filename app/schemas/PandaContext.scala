package schemas

import facades.AuthenticationFacade
import models.Member
import persists._
import schemas.mutations.Mutation
import schemas.queries.Query
import services.AuthenticationService

case class PandaContext(query: Query,
                        mutation: Mutation,
                        member: Option[Member],
                        authFacade: AuthenticationFacade,
                        spacePersist: SpacePersist,
                        requestPersist: RequestPersist,
                        memberPersist: MemberPersist,
                        reviewPersist: ReviewPersist,
                        reservationPersist: ReservationPersist,
                        authService: AuthenticationService)
