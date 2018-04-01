package schemas

import inputs.{RequestInput, SpaceInput}
import io.circe.Decoder
import io.circe.generic.AutoDerivation
import io.circe.generic.semiauto._
import models.{Member, Request, Review, Space}
import sangria.macros.derive.{deriveContextObjectType, deriveInputObjectType, deriveObjectType}
import sangria.schema.Schema
import utils.graphql.GraphqlUtil

object SchemaDefinition extends GraphqlUtil[PandaContext] {

  implicit val spaceType: Type[Space] = deriveObjectType()
  implicit val requestType: Type[Request] = deriveObjectType()
  implicit val memberType: Type[Member] = deriveObjectType()
  implicit val reviewType: Type[Review] = deriveObjectType()

  implicit val spaceInputType: InputType[SpaceInput] = deriveInputObjectType()
  implicit val requestInputType: InputType[RequestInput] = deriveInputObjectType()

  val queryType: Type[Unit] = deriveContextObjectType(_.query)
  val mutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(queryType, Some(mutationType))

}
