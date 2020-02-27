package com.kingwsi.bs.entity.role;

import com.kingwsi.bs.entity.permission.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Description: <br>
 * Comments Name: RolesAndPermissionsMapper<br>
 * Date: 2019/7/12 9:54<br>
 * Author: wangshu<br>
 */
@Mapper
public interface RolesAndPermissionsMapper {

    @Select("SELECT _r.* FROM roles _r LEFT JOIN roles_and_permissions _r_p ON _r.id = _r_p.role_id LEFT JOIN permissions _p ON _p.id = _r_p.permission_id WHERE _p.uri = #{permission.uri} AND _p.method = #{permission.method}")
    Role selectRolesByPermission(@Param("permission") Permission permission);
}
