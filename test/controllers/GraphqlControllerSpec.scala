package controllers

import play.api.test.Helpers._
import spec.BaseSpec
import io.circe.syntax._
import io.circe.generic.auto._

class GraphqlControllerSpec extends BaseSpec {

  "GET /graphql" should {

    "return 200" in {
      val queryString = s"query=${query.query}&operationName=${query.operationName.get}&variables=${query.variables.get.noSpaces}"
      val response = request(GET, s"/graphql?$queryString")

      response should haveStatus (200)
    }

    "return 400 when not provide 'query' parameter" in {
      val response = request(GET, "/graphql")

      response should haveStatus (400)
    }

  }

  "POST /graphql" should {

    "return 200" in {
      val response = request(POST, "/graphql", query asJson)

      response should haveStatus (200)
    }

    "return 400 when have no body" in {
      val response = request(POST, "/graphql")

      response should haveStatus (400)
    }

  }

  "GET /schema" should {

    "return 200" in {
      val response = request(GET, "/schema")

      response should haveStatus (200)
    }

  }

  "GET /" should {

    "return 200" in {
      val response = request(GET, "/")

      response should haveStatus (200)
    }

  }

}
