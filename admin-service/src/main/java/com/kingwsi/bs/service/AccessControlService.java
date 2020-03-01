package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RolesAndResourcesMapper;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserRepository;
import com.kingwsi.bs.entity.user.UserVO;
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
    private RolesAndResourcesMapper rolesAndResourcesMapper;

    @Autowired
    private UsersAndRolesMapper usersAndRolesMapper;

    public List<String> listRoleByUserName(String username) {
        HashSet<Role> rolesByUserName = usersAndRolesMapper.findRolesByUserName(username);
        return rolesByUserName.stream().map(Role::getName).collect(Collectors.toList());
    }

    public List<String> listRoleByUser(String userId) {
        List<Role> rolesByUserName = usersAndRolesMapper.findRolesByUserId(userId);
        return rolesByUserName.stream().map(Role::getName).collect(Collectors.toList());
    }

    public Role getRequiredRoleByResource(Resource resource) {
        return rolesAndResourcesMapper.selectRolesByResource(resource);
    }

    public UserVO getUserWithRoleByName(String username) {
        return usersAndRolesMapper.listUserWithRoles(username);
    }
}
