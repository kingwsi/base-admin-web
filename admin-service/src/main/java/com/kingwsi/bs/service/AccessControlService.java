package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RolesAndResources;
import com.kingwsi.bs.entity.user.UsersAndRoles;
import com.kingwsi.bs.mapper.RolesAndResourcesMapper;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.mapper.UsersAndRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public UserVO getUserWithRoleById(String userId) {
        return usersAndRolesMapper.listUserWithRolesById(userId);
    }

    public List<String> getRolesByUserId(String userId) {
        QueryWrapper<UsersAndRoles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).select("role_id");
        List<UsersAndRoles> usersAndRoles = usersAndRolesMapper.selectList(queryWrapper);
        return usersAndRoles.stream().map(UsersAndRoles::getRoleId).collect(Collectors.toList());
    }
}
