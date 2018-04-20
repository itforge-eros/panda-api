package schema

import entities.MultiLanguageString
import io.circe.Decoder
import models._
import models.connections.MemberDepartmentConnection
import models.edges.MemberDepartmentEdge
import models.enums.{Access, RequestStatus, ReviewEvent, SpaceCategory}
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
  implicit val materialType:                    Type[Material]                              = deriveObjectType()
  implicit val multiLanguageString:             Type[MultiLanguageString]                   = deriveObjectType()

  implicit val memberDepartmentConnection:      Type[MemberDepartmentConnection]            = deriveObjectType()
  implicit val memberDepartmentEdge:            Type[MemberDepartmentEdge]                  = deriveObjectType()

  implicit val createSpaceInput:                InputType[CreateSpaceInput]                 = deriveInputObjectType()
  implicit val createRequestInput:              InputType[CreateRequestInput]               = deriveInputObjectType()
  implicit val createReviewInput:               InputType[CreateReviewInput]                = deriveInputObjectType()
  implicit val createDepartmentInput:           InputType[CreateDepartmentInput]            = deriveInputObjectType()
  implicit val createRoleInput:                 InputType[CreateRoleInput]                  = deriveInputObjectType()
  implicit val createMaterialInput:             InputType[CreateMaterialInput]              = deriveInputObjectType()
  implicit val updateSpaceInput:                InputType[UpdateSpaceInput]                 = deriveInputObjectType()
  implicit val updateRoleInput:                 InputType[UpdateRoleInput]                  = deriveInputObjectType()
  implicit val cancelRequestInput:              InputType[CancelRequestInput]               = deriveInputObjectType()
  implicit val assignRoleInput:                 InputType[AssignRoleInput]                  = deriveInputObjectType()
  implicit val multiLanguageStringInput:        InputType[MultiLanguageStringInput]         = deriveInputObjectType()

  implicit val accessEnum:                      EnumType[Access]                            = deriveEnumType()
  implicit val requestStatusEnum:               EnumType[RequestStatus]                     = deriveEnumType()
  implicit val reviewEventEnum:                 EnumType[ReviewEvent]                       = deriveEnumType()
  implicit val spaceCategory:                   EnumType[SpaceCategory]                     = deriveEnumType()

  implicit val reviewEventDecoder: Decoder[ReviewEvent] = getDecoder(ReviewEvent(_).get)
  implicit val spaceCategoryDecoder: Decoder[SpaceCategory] = getDecoder(SpaceCategory(_).get)

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
