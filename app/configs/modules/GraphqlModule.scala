package configs.modules

import com.softwaremill.macwire.wire
import schemas.SchemeDefinition.{Mutation, Query}

trait GraphqlModule {

  lazy val query: Query = wire[Query]
  lazy val mutation: Mutation = wire[Mutation]

}
