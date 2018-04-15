package models.enums

import sangria.macros.derive.GraphQLName

sealed trait RequestStatus {

  def name: String

  override def toString = name

}

object RequestStatus {

  def apply(s: String): RequestStatus = values.find(s == _.name).get

  val values: List[RequestStatus] = List(Pending, Completed, Failed, Cancelled)

  @GraphQLName("PENDING")
  case object Pending extends RequestStatus {
    override def name = "pending"
  }

  @GraphQLName("COMPLETED")
  case object Completed extends RequestStatus {
    override def name = "completed"
  }

  @GraphQLName("FAILED")
  case object Failed extends RequestStatus {
    override def name = "failed"
  }

  @GraphQLName("CANCELLED")
  case object Cancelled extends RequestStatus {
    override def name = "cancelled"
  }

}
