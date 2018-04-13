package models

sealed trait RequestStatus {

  def code: String

}

object RequestStatus {

  def apply(s: String): RequestStatus = values.find(s == _.code).get

  val values: List[RequestStatus] = List(Pending, Approved, Rejected, Cancelled)

  case object Pending extends RequestStatus {
    override def code = "pending"
  }

  case object Approved extends RequestStatus {
    override def code = "approved"
  }

  case object Rejected extends RequestStatus {
    override def code = "rejected"
  }

  case object Cancelled extends RequestStatus {
    override def code = "cancelled"
  }

}



