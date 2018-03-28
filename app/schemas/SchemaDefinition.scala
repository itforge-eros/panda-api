package schemas

import models._
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

object SchemaDefinition extends GraphqlUtil {

  implicit val SpaceType: Type[Space] = deriveObjectType()
  implicit val RequestType: Type[Request] = deriveObjectType()
  implicit val MemberType: Type[Member] = deriveObjectType()
  implicit val ApprovalType: Type[Approval] = deriveObjectType()

  val QueryType: Type[Unit] = deriveContextObjectType(_.query)
  val MutationType: Type[Unit] = deriveContextObjectType(_.mutation)

  val schema = Schema(QueryType, Some(MutationType))

}
