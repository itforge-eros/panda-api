package models

import java.util.UUID

import entities.MemberDepartmentRelationEntity

class Identity(member: Member, relations: List[MemberDepartmentRelationEntity]) {

  def viewer: Member = member

  def department(id: UUID): Option[Resource] = idResourceMapper.get(id)

  def department(department: String): Option[Resource] = departmentNameResourceMapper.get(department.toLowerCase)

  def departments: List[Department] = relations map (_.department) map Department.of distinct

  lazy private val departmentNameResourceMapper: Map[String, Resource] = relations
    .groupBy(_.department.name.toLowerCase)
    .mapValues { relations => {
      val roles = relations map (_.role) map Role.of
      new Resource(roles)
    }}

  lazy private val idResourceMapper: Map[UUID, Resource] = relations
    .groupBy(_.department.id)
    .mapValues { relations => {
      val roles = relations map (_.role) map Role.of
      new Resource(roles)
    }}

}
