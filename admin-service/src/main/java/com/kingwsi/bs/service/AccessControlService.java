package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingwsi.bs.common.enumerate.RedisKeyEnum;
import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.mapper.*;
import com.kingwsi.bs.entity.user.UserVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
     * 获取完整用户信息
     *
     * @param userId
     * @return
     */
    public UserVO getUserWithRoleById(String userId) {
        Object object = redisTemplate.opsForValue().get(RedisKeyEnum.USER_AUTH_INFO + userId);
        if (object instanceof UserVO) {
            return (UserVO) object;
        } else {
            UserVO userVO = usersAndRolesMapper.listUserWithRolesById(userId);
            redisTemplate.opsForValue().set(RedisKeyEnum.USER_AUTH_INFO + userId, userVO, RedisKeyEnum.USER_AUTH_INFO.getExpire(), TimeUnit.MINUTES);
            return userVO;
        }
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

    public List<String> getRoleIdsByUserId(String userId) {
        List<Role> roles = getRolesByUserId(userId);
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }

    public Principal getPrincipal(String userId) {
        Principal principal = new Principal();
        principal.setRoles(this.getRolesByUserId(userId));
        principal.setUser(userMapper.selectById(userId));
        return principal;
    }

    public Resource getResourceByUriAndMethod(String requestURI, String method) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uri", requestURI).like("method", method);
        return resourceService.getOne(queryWrapper);
    }

    public List<String> getUriByUser(String userId) {
        return resourceMapper.selectUriByUser(userId);
    }

    public List<Resource> listByUser(String userId){
        return resourceMapper.selectByUser(userId);
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
}
