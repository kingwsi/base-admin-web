package com.kingwsi.bs.entity.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: <br>
 * Comments Name: UsersAndRolesRepository<br>
 * Date: 2019/7/11 17:41<br>
 * Author: Administrator<br>
 */
@Repository
public interface UsersAndRolesRepository extends CrudRepository<UsersAndRoles, String> {
}
