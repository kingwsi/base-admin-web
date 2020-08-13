package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.common.exception.CustomException;
import com.kingwsi.bs.mapper.*;
import com.kingwsi.bs.entity.user.UserVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 访问控制服务<br>
 * Comments Name: AccessControlService<br>
 * Date: 2019/7/12 10:14<br>
 * Author: wangshu<br>
 */
@Service
public class AccessControlService {

    private final UsersAndRolesMapper usersAndRolesMapper;

    private final RoleMapper roleMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final UserMapper userMapper;

    private final ResourceService resourceService;

    private final ResourceMapper resourceMapper;

    public AccessControlService(UsersAndRolesMapper usersAndRolesMapper, RoleMapper roleMapper, RedisTemplate<String, Object> redisTemplate, UserMapper userMapper, ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
        this.usersAndRolesMapper = usersAndRolesMapper;
        this.roleMapper = roleMapper;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.resourceService = resourceService;
    }

    /**
     * 查询用户拥有的角色列表
     *
     * @param userId
     * @return
     */
    public List<Role> getRolesByUserId(String userId) {
        return roleMapper.selectByUserId(userId);
    }

    public Principal getPrincipal(String userId) {
        Principal principal = new Principal();
        principal.setRoles(this.getRolesByUserId(userId));
        principal.setUser(userMapper.selectById(userId));
        return principal;
    }

    /**
     * 查询用户所拥有的url类型资源，并按请求方式过滤
     * @param userId
     * @param method
     * @return
     */
    public List<Resource> listByUserAndMethod(String userId, String method) {
        return resourceMapper.selectByUserAndMethod(userId, method);
    }

    public UserVO getUserWithRoleByUsername(String username) {
        UserVO userVO = usersAndRolesMapper.listUserWithRolesByUsername(username);
        if (userVO == null) {
            throw new CustomException("获取用户信息失败");
        }
        return userVO;
    }
}
