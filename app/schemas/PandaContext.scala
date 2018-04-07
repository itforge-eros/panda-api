package schemas

import facades.AuthFacade
import models.Member
import persists._
import schemas.mutations.Mutation
import schemas.queries.Query
import services.AuthService

case class PandaContext(query: Query,
                        mutation: Mutation,
                        authFacade: AuthFacade,
                        member: Member,
                        spacePersist: SpacePersist,
                        requestPersist: RequestPersist,
                        memberPersist: MemberPersist,
                        reviewPersist: ReviewPersist,
                        reservationPersist: ReservationPersist,
                        authService: AuthService)
