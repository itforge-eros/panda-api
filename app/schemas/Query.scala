package schemas

import schemas.queries.{MemberQuery, RequestQuery, SpaceQuery}

class Query extends SpaceQuery
  with MemberQuery
  with RequestQuery
