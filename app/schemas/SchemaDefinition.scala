package schemas

import inputs.{RequestInput, ReviewInput, SpaceInput}
import models._
import sangria.execution.FieldTag
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
  implicit val requestStatusType: EnumType[RequestStatus] = deriveEnumType()

  implicit val spaceInputType: InputType[SpaceInput] = deriveInputObjectType()
  implicit val requestInputType: InputType[RequestInput] = deriveInputObjectType()
  implicit val reviewInputType: InputType[ReviewInput] = deriveInputObjectType()

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
