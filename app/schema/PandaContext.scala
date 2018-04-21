package schema

import facades._
import models.Identity
import schema.mutation.Mutation
import schema.query.Query

case class PandaContext(query: Query,
                        mutation: Mutation,
                        identity: Option[Identity],
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
                        materialFacade: MaterialFacade,
                        problemFacade: ProblemFacade)
