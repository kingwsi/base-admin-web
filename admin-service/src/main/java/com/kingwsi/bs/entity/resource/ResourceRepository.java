package com.kingwsi.bs.entity.resource;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br>
 * Comments Name: ResourceRepository<br>
 * Date: 2019/7/11 16:39<br>
 * Author: wangshu<br>
 */
@Repository
public interface ResourceRepository extends CrudRepository<Resource, String> {
}
