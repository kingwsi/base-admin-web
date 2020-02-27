package com.kingwsi.bs.entity.role;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description: <br>
 * Comments Name: RolesAndPermissionsRepository<br>
 * Date: 2019/7/11 16:43<br>
 * Author: wangshu<br>
 */
public interface RolesAndPermissionsRepository extends CrudRepository<RolesAndPermissions, String> {
    List<RolesAndPermissions> findByRoleId(String roleId);

}
