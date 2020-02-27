package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.role.RoleRepository;
import com.kingwsi.bs.entity.role.RolesAndPermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 角色服务<br>
 * Comments Name: RoleService<br>
 * Date: 2019/7/11 16:52<br>
 * Author: Administrator<br>
 */
@Service
public class RoleService {

    @Autowired
    private RolesAndPermissionsRepository rolesAndPermissionsRepository;

    @Autowired
    private RoleRepository roleRepository;

}
