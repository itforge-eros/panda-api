package schemas

import facades.{AuthenticationFacade, SpaceFacade}
import models.Member
import persists._
import schemas.mutations.Mutation
import schemas.queries.Query
import services.AuthenticationService

case class PandaContext(query: Query,
                        mutation: Mutation,
                        member: Option[Member],
                        authenticationFacade: AuthenticationFacade,
                        spaceFacade: SpaceFacade,
                        spacePersist: SpacePersist,
                        requestPersist: RequestPersist,
                        memberPersist: MemberPersist,
                        reviewPersist: ReviewPersist,
                        reservationPersist: ReservationPersist,
                        authorizationService: AuthenticationService)
