package models

import schema.PandaContext
import utils.Functional
import utils.graphql.GraphqlUtil
import validators.AuthorizationValidator

trait BaseModel extends GraphqlUtil[PandaContext]
  with AuthorizationValidator
  with Functional
