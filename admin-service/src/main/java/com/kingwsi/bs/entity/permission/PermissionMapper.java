package com.kingwsi.bs.entity.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PermissionMapper {

    @Select("select p.* from user_and_roles u_r left join roles_and_permissions r_p ON u_r.role_id = r_p.role_id LEFT JOIN permissions p ON r_p.permission_id = p.id  WHERE u_r.user_id = '1'")
    Integer checkPermissionByUserId(@Param("id") String id,@Param("method") String method, @Param("api") String api);
}
