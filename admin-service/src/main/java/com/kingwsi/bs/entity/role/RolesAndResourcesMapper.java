package com.kingwsi.bs.entity.role;

import com.kingwsi.bs.entity.resource.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Description: <br>
 * Comments Name: RolesAndResourcesMapper<br>
 * Date: 2019/7/12 9:54<br>
 * Author: wangshu<br>
 */
@Component
@Mapper
public interface RolesAndResourcesMapper {

    @Select("SELECT _r.* FROM roles _r LEFT JOIN roles_and_resources _r_p ON _r.id = _r_p.role_id LEFT JOIN resources _p ON _p.id = _r_p.resource_id WHERE _p.uri = #{resource.uri} AND _p.method = #{resource.method}")
    Role selectRolesByResource(@Param("resource") Resource resource);
}
