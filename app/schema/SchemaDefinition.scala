package schema

import io.circe.{Decoder, HCursor}
import models._
import models.connections.MemberDepartmentsConnection
import models.edges.MemberDepartmentsEdge
import models.enums.{Access, RequestStatus, ReviewEvent}
import models.inputs._
import sangria.macros.derive._
import sangria.schema.{EnumType, Schema}
import utils.graphql.GraphqlUtil

object SchemaDefinition extends GraphqlUtil[PandaContext] {

  implicit val spaceType:                       Type[Space]                                 = deriveObjectType()
  implicit val requestType:                     Type[Request]                               = deriveObjectType()
  implicit val memberType:                      Type[Member]                                = deriveObjectType()
  implicit val memberWithTokenType:             Type[MemberWithToken]                       = deriveObjectType()
  implicit val reviewType:                      Type[Review]                                = deriveObjectType()
  implicit val reservationType:                 Type[Reservation]                           = deriveObjectType()
  implicit val departmentType:                  Type[Department]                            = deriveObjectType()
  implicit val roleType:                        Type[Role]                                  = deriveObjectType()
  implicit val permissionType:                  Type[Permission]                            = deriveObjectType()
  implicit val memberDepartmentsConnection:     Type[MemberDepartmentsConnection]           = deriveObjectType()
  implicit val memberDepartmentsEdge:           Type[MemberDepartmentsEdge]                 = deriveObjectType()

  implicit val accessEnum:                      EnumType[Access]                            = deriveEnumType()
  implicit val requestStatusEnum:               EnumType[RequestStatus]                     = deriveEnumType()
  implicit val reviewEventEnum:                 EnumType[ReviewEvent]                       = deriveEnumType()

  implicit val createSpaceInput:                InputType[CreateSpaceInput]                 = deriveInputObjectType()
  implicit val createRequestInput:              InputType[CreateRequestInput]               = deriveInputObjectType()
  implicit val createReviewInput:               InputType[CreateReviewInput]                = deriveInputObjectType()
  implicit val createDepartmentInput:           InputType[CreateDepartmentInput]            = deriveInputObjectType()
  implicit val createRoleInput:                 InputType[CreateRoleInput]                  = deriveInputObjectType()

  implicit val reviewEventDecoder: Decoder[ReviewEvent] = (c: HCursor) => Decoder.decodeString.map(ReviewEvent.apply)(c)

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
