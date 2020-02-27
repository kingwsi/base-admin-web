package com.kingwsi.bs.entity.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: <br>
 * Comments Name: RoleRepository<br>
 * Date: 2019/7/11 16:41<br>
 * Author: wangshu<br>
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
}
