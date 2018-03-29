package utils.graphql

import io.circe.Json

case class GraphqlQuery(query: String,
                        operationName: Option[String] = None,
                        variables: Option[Json] = None)
