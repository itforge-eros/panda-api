package config.components

import com.softwaremill.macwire.wire
import schema.mutation.Mutation
import schema.query.Query

trait GraphqlComponents {

  lazy val query: Query = wire[Query]
  lazy val mutation: Mutation = wire[Mutation]

}
