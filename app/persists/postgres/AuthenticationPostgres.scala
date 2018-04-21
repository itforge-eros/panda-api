package persists.postgres

import java.util.UUID

import anorm.SqlParser._
import anorm._
import entities.{DepartmentEntity, MemberEntity, RoleEntity}
import persists.{AuthenticationPersist, MemberDepartmentRelationEntity}
import play.api.db.Database

class AuthenticationPostgres(db: Database) extends AuthenticationPersist {

  override def findByMemberId(memberId: UUID): List[MemberDepartmentRelationEntity] = db.withConnection { implicit connection =>
    SQL"""
         SELECT * FROM department
         JOIN role ON role.department_id = department.id
         JOIN member_role ON member_role.role_id = role.id
         JOIN member ON member.id = member_role.member_id
         WHERE member_id = $memberId::uuid
       """ as memberDepartmentRelationEntityParser.* map {
      case memberUsername ~
        memberFirstName ~
        memberLastName ~
        memberEmail ~
        roleId ~
        roleName ~
        roleDescription ~
        rolePermissions ~
        departmentId ~
        departmentName ~
        departmentFullEnglishName ~
        departmentFullThaiName ~
        departmentDescription
      => MemberDepartmentRelationEntity(
        MemberEntity(
          memberId,
          memberUsername,
          memberFirstName,
          memberLastName,
          memberEmail
        ),
        RoleEntity(
          roleId,
          roleName,
          roleDescription,
          rolePermissions,
          departmentId
        ),
        DepartmentEntity(
          departmentId,
          departmentName,
          departmentFullEnglishName,
          departmentFullThaiName,
          departmentDescription
        )
      )
    }
  }


  private val memberDepartmentRelationEntityParser = {
    str("member.username") ~
    str("member.first_name") ~
    str("member.last_name") ~
    str("member.email") ~
    get[UUID]("role.id") ~
    str("role.name") ~
    get[Option[String]]("role.description") ~
    get[List[String]]("role.permissions") ~
    get[UUID]("department.id") ~
    str("department.name") ~
    str("department.full_english_name") ~
    str("department.full_thai_name") ~
    get[Option[String]]("department.description")
  }

}
