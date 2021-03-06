package schema

import sangria.execution.{BeforeFieldResult, Middleware, MiddlewareBeforeField, MiddlewareQueryContext}
import sangria.schema.Context

class SecurityMiddleware extends Middleware[PandaContext]
  with MiddlewareBeforeField[PandaContext] {

  override type QueryVal = Unit
  override type FieldVal = Unit

  override def beforeQuery(context: MiddlewareQueryContext[PandaContext, _, _]): Unit = ()

  override def afterQuery(queryVal: QueryVal,
                          context: MiddlewareQueryContext[PandaContext, _, _]): Unit = ()

  override def beforeField(queryVal: QueryVal,
                           middlewareContext: MiddlewareQueryContext[PandaContext, _, _],
                           context: Context[PandaContext, _]): BeforeFieldResult[PandaContext, QueryVal] = {
    continue
  }

}
