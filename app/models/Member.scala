package models

import java.util.UUID

import context.BaseContext
import sangria.schema.{Field, ObjectType, StringType, fields}
import utils.GraphqlUtil

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String)

object Member extends GraphqlUtil {

  lazy val Type: ObjectType[BaseContext, Member] = ObjectType("member",
    () => fields[BaseContext, Member](
      Field("id", UUIDType, resolve = _.value.id),
      Field("firstName", StringType, resolve = _.value.firstName),
      Field("lastName", StringType, resolve = _.value.lastName),
      Field("email", StringType, resolve = _.value.email)
    )
  )

}
