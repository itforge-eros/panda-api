package schema

import entities.MultiLanguageString
import io.circe.Decoder
import models._
import models.connections.MemberDepartmentConnection
import models.edges.MemberDepartmentEdge
import models.enums.{Access, RequestStatus, ReviewEvent}
import models.inputs._
import sangria.macros.derive._
import sangria.schema.{EnumType, Schema}
import utils.graphql.GraphqlUtil

object SchemaDefinition extends GraphqlUtil[PandaContext] {

  implicit val memberType:                      Type[Member]                                = deriveObjectType()
  implicit val memberWithTokenType:             Type[MemberWithToken]                       = deriveObjectType()
  implicit val departmentType:                  Type[Department]                            = deriveObjectType()
  implicit val spaceType:                       Type[Space]                                 = deriveObjectType()
  implicit val requestType:                     Type[Request]                               = deriveObjectType()
  implicit val reviewType:                      Type[Review]                                = deriveObjectType()
  implicit val roleType:                        Type[Role]                                  = deriveObjectType()
  implicit val reservationType:                 Type[Reservation]                           = deriveObjectType()
  implicit val problemType:                     Type[Problem]                               = deriveObjectType()
  implicit val permissionType:                  Type[Permission]                            = deriveObjectType()
  implicit val materialType:                    Type[Material]                              = deriveObjectType()
  implicit val multiLanguageStringType:         Type[MultiLanguageString]                   = deriveObjectType()
  implicit val memberDepartmentConnection:      Type[MemberDepartmentConnection]            = deriveObjectType()
  implicit val memberDepartmentEdge:            Type[MemberDepartmentEdge]                  = deriveObjectType()

  implicit val createDepartmentInput:           InputType[CreateDepartmentInput]            = deriveInputObjectType()
  implicit val createSpaceInput:                InputType[CreateSpaceInput]                 = deriveInputObjectType()
  implicit val updateSpaceInput:                InputType[UpdateSpaceInput]                 = deriveInputObjectType()
  implicit val deleteSpaceInput:                InputType[DeleteSpaceInput]                 = deriveInputObjectType()
  implicit val uploadSpaceImageInput:           InputType[UploadSpaceImageInput]            = deriveInputObjectType()
  implicit val createRequestInput:              InputType[CreateRequestInput]               = deriveInputObjectType()
  implicit val cancelRequestInput:              InputType[CancelRequestInput]               = deriveInputObjectType()
  implicit val createReviewInput:               InputType[CreateReviewInput]                = deriveInputObjectType()
  implicit val createRoleInput:                 InputType[CreateRoleInput]                  = deriveInputObjectType()
  implicit val updateRoleInput:                 InputType[UpdateRoleInput]                  = deriveInputObjectType()
  implicit val deleteRoleInput:                 InputType[DeleteRoleInput]                  = deriveInputObjectType()
  implicit val assignRoleInput:                 InputType[AssignRoleInput]                  = deriveInputObjectType()
  implicit val revokeRoleInput:                 InputType[RevokeRoleInput]                  = deriveInputObjectType()
  implicit val createProblemInput:              InputType[CreateProblemInput]               = deriveInputObjectType()
  implicit val createMaterialInput:             InputType[CreateMaterialInput]              = deriveInputObjectType()
  implicit val deleteMaterialInput:             InputType[DeleteMaterialInput]              = deriveInputObjectType()
  implicit val multiLanguageStringInput:        InputType[MultiLanguageStringInput]         = deriveInputObjectType()

  implicit val accessEnum:                      EnumType[Access]                            = deriveEnumType()
  implicit val requestStatusEnum:               EnumType[RequestStatus]                     = deriveEnumType()
  implicit val reviewEventEnum:                 EnumType[ReviewEvent]                       = deriveEnumType()

  implicit val reviewEventDecoder: Decoder[ReviewEvent] = getDecoder(ReviewEvent(_).get)

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
