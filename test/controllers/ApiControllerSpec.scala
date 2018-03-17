package controllers

import play.api.test.Helpers._
import spec.BaseSpec

class ApiControllerSpec extends BaseSpec {

  "GET /graphql" should {

    "return 200" in {
      val response = request(GET, s"/graphql?query=$query")

      response should haveStatus (200)
    }

  }

  "POST /graphql" should {

    "return 200" in {
      val response = request(POST, "/graphql", graphqlBody)

      response should haveStatus (200)
    }

  }

}
