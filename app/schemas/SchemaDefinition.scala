package schemas

import io.circe.Decoder
import io.circe.generic.semiauto._
import models.{Review, Member, Request, Space}
import sangria.macros.derive.{deriveContextObjectType, deriveInputObjectType, deriveObjectType}
import sangria.schema.{InputType, Schema}
import schemas.inputs.SpaceInput
import utils.graphql.GraphqlUtil

object SchemaDefinition extends GraphqlUtil {

  implicit val spaceType: Type[Space] = deriveObjectType()
  implicit val requestType: Type[Request] = deriveObjectType()
  implicit val memberType: Type[Member] = deriveObjectType()
  implicit val reviewType: Type[Review] = deriveObjectType()

  implicit val spaceInputType: InputType[SpaceInput] = deriveInputObjectType[SpaceInput]()
  implicit val spaceInputJson: Decoder[SpaceInput] = deriveDecoder

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
