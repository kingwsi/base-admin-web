package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RoleRepository;
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
    private RoleRepository roleRepository;

    public Iterable<Role> listRoles(){
        return roleRepository.findAll();
    }

}
