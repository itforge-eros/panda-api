package schemas

import models._
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

object SchemaDefinition extends GraphqlUtil {

  implicit val SpaceType: CustomType[Space] = deriveObjectType()
  implicit val RequestType: CustomType[Request] = deriveObjectType()
  implicit val MemberType: CustomType[Member] = deriveObjectType()

  val QueryType: CustomType[Unit] = deriveContextObjectType(_.query)
  val MutationType: CustomType[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(QueryType, Some(MutationType))

}
