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
                        reservationFacade: ReservationFacade,
                        groupFacade: GroupFacade)
