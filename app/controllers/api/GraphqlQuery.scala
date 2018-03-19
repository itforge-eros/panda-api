package controllers.api

import io.circe.Json

case class GraphqlQuery(query: String,
                        operation: Option[String] = None,
                        variables: Option[Json] = None)
