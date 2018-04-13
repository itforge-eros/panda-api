package schemas.mutations

import java.util.UUID

import models.{BaseModel, Department}
import sangria.macros.derive.GraphQLField
import schemas.inputs.DepartmentInput

import scala.util.Success

trait DepartmentMutation extends BaseModel {

  @GraphQLField
  def createDepartment(input: DepartmentInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    Success(Department(
      UUID.randomUUID(),
      "random name",
      Some("whatever")
    ))
  }

}
