package configs.modules

import com.softwaremill.macwire.wire
import schemas.mutations.Mutation
import schemas.queries.Query

trait GraphqlModule {

  lazy val query: Query = wire[Query]
  lazy val mutation: Mutation = wire[Mutation]

}
