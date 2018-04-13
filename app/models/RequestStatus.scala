package models

sealed trait RequestStatus {

  def code: String

}

object RequestStatus {

  def apply(s: String): RequestStatus = values.find(s == _.code).get

  val values: List[RequestStatus] = List(Pending, Success, Failed, Cancelled)

  case object Pending extends RequestStatus {
    override def code = "pending"
  }

  case object Success extends RequestStatus {
    override def code = "success"
  }

  case object Failed extends RequestStatus {
    override def code = "failed"
  }

  case object Cancelled extends RequestStatus {
    override def code = "cancelled"
  }

}



