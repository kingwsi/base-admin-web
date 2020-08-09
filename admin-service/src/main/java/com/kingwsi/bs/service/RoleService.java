package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.resource.ResourceVO;
import com.kingwsi.bs.entity.role.*;
import com.kingwsi.bs.mapper.ResourceMapper;
import com.kingwsi.bs.mapper.RoleMapper;
import com.kingwsi.bs.mapper.RolesAndResourcesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 角色服务<br>
 * Comments Name: RoleService<br>
 * Date: 2019/7/11 16:52<br>
 * Author: Administrator<br>
 */
@Service
public class RoleService {

    private final RoleMapper roleMapper;

    private final ResourceMapper resourceMapper;

    private final RolesAndResourcesMapper rolesAndResourcesMapper;

    public RoleService(RoleMapper roleMapper, ResourceMapper resourceMapper, RolesAndResourcesMapper rolesAndResourcesMapper) {
        this.roleMapper = roleMapper;
        this.resourceMapper = resourceMapper;
        this.rolesAndResourcesMapper = rolesAndResourcesMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createRoleVO(RoleVO roleVO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);
        this.roleMapper.insert(role);
        roleVO.getResourceIds().forEach(id -> rolesAndResourcesMapper.insert(new RolesAndResources(role.getId(), id)));
    }

    public void deleteById(String id) {
        this.roleMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateById(RoleVO roleVO) {
        QueryWrapper<RolesAndResources> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleVO.getId());
        rolesAndResourcesMapper.delete(queryWrapper);
        rolesAndResourcesMapper.batchInsertRoleResources(roleVO.getId(), roleVO.getResourceIds());
        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);
        roleMapper.updateById(role);
    }

    public IPage<RoleVO> listOfPages(Page<RoleVO> page, RoleVO roleVO) {
        return roleMapper.selectPageWithResources(page, roleVO);
    }
}
