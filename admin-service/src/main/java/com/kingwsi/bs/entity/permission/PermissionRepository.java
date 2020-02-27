package com.kingwsi.bs.entity.permission;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: <br>
 * Comments Name: PermissionRepository<br>
 * Date: 2019/7/11 16:39<br>
 * Author: wangshu<br>
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, String> {

    @Query(value = "select p.* from user_and_roles u_r left join roles_and_permissions r_p ON u_r.role_id = r_p.role_id LEFT JOIN permissions p ON r_p.permission_id = p.id  WHERE u_r.user_id = '1'",nativeQuery = true)
    Integer checkPermissionByUserId(String id);
}
