package com.kingwsi.bs.entity.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: []
 * Name: UserRepository
 * Author: wangshu
 * Date: 2019/6/29 15:52
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
