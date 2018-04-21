package entities

case class MemberDepartmentRelationEntity(member: MemberEntity,
                                          role: RoleEntity,
                                          department: DepartmentEntity)
