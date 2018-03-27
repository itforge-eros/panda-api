package schemas

import java.time.Instant
import java.util.UUID
import java.util.UUID.randomUUID

import context.BaseContext
import models.{Member, Space}
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

object SchemeDefinition extends GraphqlUtil {

  val id = Argument("id", UuidType)
  val name = Argument("name", StringType)
  val description = Argument("description", OptionInputType(StringType))
  val capacity = Argument("capacity", IntType)
  val requiredApproval = Argument("requiredApproval", IntType)
  val isReservable = Argument("isReservable", BooleanType)

  class Query {

    @GraphQLField
    def space(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.space.find(id)

    @GraphQLField
    def spaces(ctx: AppContext[Unit]) = ctx.ctx.space.findAll

    @GraphQLField
    def member(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.member.find(id)

  }

  class Mutation {

    @GraphQLField
    def createSpace(name: String,
                    description: Option[String],
                    capacity: Int,
                    requiredApproval: Int,
                    isReservable: Boolean)
                   (ctx: AppContext[Unit]) = {
      val space = Space(
        randomUUID(),
        ctx.arg("name"),
        ctx.argOpt("description"),
        ctx.arg("capacity"),
        ctx.arg("requiredApproval"),
        ctx.arg("isReservable"),
        Instant.now()
      )

      ctx.ctx.space.insert(space).get
    }

  }

  val QueryType = deriveContextObjectType[BaseContext, Query, Unit](_.query)
  val MutationType = deriveContextObjectType[BaseContext, Mutation, Unit](_.mutation)

  val schema = Schema(QueryType, Some(MutationType))

}
