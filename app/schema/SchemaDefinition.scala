package schema

import io.circe.{Decoder, HCursor}
import models._
import models.enums.{Access, RequestStatus, ReviewEvent}
import models.inputs.{CreateDepartmentInput, CreateRequestInput, CreateReviewInput, CreateSpaceInput}
import sangria.macros.derive._
import sangria.schema.{EnumType, Schema}
import utils.graphql.GraphqlUtil

object SchemaDefinition extends GraphqlUtil[PandaContext] {

  implicit val spaceType: Type[Space] = deriveObjectType()
  implicit val requestType: Type[Request] = deriveObjectType()
  implicit val memberType: Type[Member] = deriveObjectType()
  implicit val reviewType: Type[Review] = deriveObjectType()
  implicit val reservationType: Type[Reservation] = deriveObjectType()
  implicit val memberWithToken: Type[MemberWithToken] = deriveObjectType()
  implicit val departmentType: Type[Department] = deriveObjectType()
  implicit val roleType: Type[Role] = deriveObjectType()
  implicit val permissionType: Type[Permission] = deriveObjectType()

  implicit val accessType: EnumType[Access] = deriveEnumType()
  implicit val requestStatusEnumType: EnumType[RequestStatus] = deriveEnumType()
  implicit val reviewEventEnumType: EnumType[ReviewEvent] = deriveEnumType()

  implicit val reviewEventDecoder: Decoder[ReviewEvent] = (c: HCursor) => Decoder.decodeString.map(ReviewEvent.apply).apply(c)

  implicit val spaceInputType: InputType[CreateSpaceInput] = deriveInputObjectType()
  implicit val requestInputType: InputType[CreateRequestInput] = deriveInputObjectType()
  implicit val reviewInputType: InputType[CreateReviewInput] = deriveInputObjectType()
  implicit val departmentInputType: InputType[CreateDepartmentInput] = deriveInputObjectType()

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}