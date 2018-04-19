package models.enums

import sangria.macros.derive.GraphQLName

sealed trait ReviewEvent {

  def name: String

  override def toString = name

}

object ReviewEvent {

  def apply(s: String): Option[ReviewEvent] = values.find(s equalsIgnoreCase _.name)

  val values: List[ReviewEvent] = List(Approve, Reject, Comment)

  @GraphQLName("APPROVE")
  case object Approve extends ReviewEvent {
    override def name = "approve"
  }

  @GraphQLName("REJECT")
  case object Reject extends ReviewEvent {
    override def name = "reject"
  }

  @GraphQLName("COMMENT")
  case object Comment extends ReviewEvent {
    override def name = "comment"
  }

}
