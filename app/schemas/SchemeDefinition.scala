package schemas

import context.BaseContext
import models.{Mutation, Query}
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

object SchemeDefinition extends GraphqlUtil {

  val QueryType = deriveContextObjectType[BaseContext, Query, Unit](_.query)
  val MutationType = deriveContextObjectType[BaseContext, Mutation, Unit](_.mutation)

  val schema = Schema(QueryType, Some(MutationType))

}
