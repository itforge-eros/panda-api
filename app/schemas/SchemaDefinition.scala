package schemas

import models.{Approval, Member, Request, Space}
import sangria.macros.derive.{deriveContextObjectType, deriveInputObjectType, deriveObjectType}
import sangria.schema.{InputType, Schema}
import schemas.inputs.SpaceInput
import utils.graphql.GraphqlUtil
import sangria.marshalling.circe.circeFromInput

object SchemaDefinition extends GraphqlUtil {

  implicit val spaceType: Type[Space] = deriveObjectType()
  implicit val requestType: Type[Request] = deriveObjectType()
  implicit val memberType: Type[Member] = deriveObjectType()
  implicit val approvalType: Type[Approval] = deriveObjectType()

  implicit val spaceInputType: InputType[SpaceInput] = deriveInputObjectType[SpaceInput]()

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
