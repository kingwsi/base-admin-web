package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.permission.Permission;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RolesAndPermissionsMapper;
import com.kingwsi.bs.entity.user.UsersAndRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 访问控制服务<br>
 * Comments Name: AccessControlService<br>
 * Date: 2019/7/12 10:14<br>
 * Author: wangshu<br>
 */
@Service
public class AccessControlService {

    @Autowired
    private RolesAndPermissionsMapper rolesAndPermissionsMapper;

    @Autowired
    private UsersAndRolesMapper usersAndRolesMapper;

    public List<String> listRoleByUserName(String username) {
        HashSet<Role> rolesByUserName = usersAndRolesMapper.findRolesByUserName(username);
        return rolesByUserName.stream().map(Role::getName).collect(Collectors.toList());
    }

    public Role getRequiredRoleByPermission(Permission permission) {
        return rolesAndPermissionsMapper.selectRolesByPermission(permission);
    }
}
