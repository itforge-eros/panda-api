package models

case class MemberWithToken(member: Member,
                           token: String) extends BaseModel
