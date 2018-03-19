package forms

import io.circe.{Json, JsonObject}

case class GraphqlQuery(query: String,
                        operation: Option[String],
                        variables: Option[Json])
