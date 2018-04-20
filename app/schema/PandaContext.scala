package schema

import facades._
import models.Member
import persists._
import schema.mutation.Mutation
import schema.query.Query
import services.AuthenticationService

case class PandaContext(query: Query,
                        mutation: Mutation,
                        member: Option[Member],
                        authenticationFacade: AuthenticationFacade,
                        authorizationFacade: AuthorizationFacade,
                        spaceFacade: SpaceFacade,
                        memberFacade: MemberFacade,
                        requestFacade: RequestFacade,
                        reviewFacade: ReviewFacade,
                        reservationFacade: ReservationFacade,
                        departmentFacade: DepartmentFacade,
                        roleFacade: RoleFacade,
                        permissionFacade: PermissionFacade,
                        materialFacade: MaterialFacade)
