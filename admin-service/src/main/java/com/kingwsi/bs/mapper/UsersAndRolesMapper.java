package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.entity.user.UsersAndRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * Description: mybatis持久层接口<br>
 * Comments Name: UsersAndRolesMapper<br>
 * Date: 2019/7/11 18:18<br>
 * Author: Administrator<br>
 */
@Component
@Mapper
public interface UsersAndRolesMapper extends BaseMapper<UsersAndRoles> {

    @Select("SELECT _r.* FROM sys_roles _r LEFT JOIN user_and_roles _u_r ON _r.id = _u_r.role_id WHERE _u_r.user_id = #{id}")
    List<Role> findRolesByUserId(@Param("id") String id);

    @Select("SELECT _r.* FROM sys_roles _r LEFT JOIN sys_users_and_roles _u_r ON _r.id = _u_r.role_id LEFT JOIN sys_users _u ON _u_r.user_id = _u.id WHERE _u.username = #{username}")
    HashSet<Role> findRolesByUserName(@Param("username") String username);

    UserVO listUserWithRoles(@Param("userName") String userName);

    UserVO listUserWithRolesByUsername(@Param("username") String username);

    Integer batchInsert(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

    void deleteByUserId(@Param("userId") String userId);
}
