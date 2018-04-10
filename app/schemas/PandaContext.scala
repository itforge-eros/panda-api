package schemas

import facades._
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
                        memberFacade: MemberFacade,
                        requestFacade: RequestFacade,
                        reviewFacade: ReviewFacade,
                        spacePersist: SpacePersist,
                        requestPersist: RequestPersist,
                        memberPersist: MemberPersist,
                        reviewPersist: ReviewPersist,
                        reservationPersist: ReservationPersist,
                        authorizationService: AuthenticationService)
