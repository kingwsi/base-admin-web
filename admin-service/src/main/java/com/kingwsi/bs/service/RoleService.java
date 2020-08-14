package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.role.*;
import com.kingwsi.bs.mapper.RoleMapper;
import com.kingwsi.bs.mapper.RolesAndResourcesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final RoleConvertMapper roleConvertMapper;

    private final RolesAndResourcesMapper rolesAndResourcesMapper;

    public RoleService(RoleMapper roleMapper, RoleConvertMapper roleConvertMapper, RolesAndResourcesMapper rolesAndResourcesMapper) {
        this.roleMapper = roleMapper;
        this.roleConvertMapper = roleConvertMapper;
        this.rolesAndResourcesMapper = rolesAndResourcesMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createRoleVO(RoleVO roleVO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVO, role);
        this.roleMapper.insert(role);
        rolesAndResourcesMapper.batchInsertRoleResources(role.getId(), roleVO.getResourceIdList());
    }

    public void deleteById(String id) {
        this.roleMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateById(RoleVO roleVO) {
        QueryWrapper<RolesAndResources> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleVO.getId());
        rolesAndResourcesMapper.delete(queryWrapper);
        rolesAndResourcesMapper.batchInsertRoleResources(roleVO.getId(), roleVO.getResourceIdList());
        Role role = roleConvertMapper.toRole(roleVO);
        roleMapper.updateById(role);
    }

    public IPage<RoleVO> listOfPages(Page<RoleVO> page, RoleVO roleVO) {
        return roleMapper.selectPageWithResources(page, roleVO);
    }

    /**
     * 根据id获取角色信息
     * @param id
     * @return
     */
    public RoleVO getRoleWithResources(String id) {
        return roleMapper.selectRoleWithResources(id);
    }

    public List<RoleVO> list(RoleVO vo) {
        List<Role> roles = roleMapper.selectList(Wrappers.emptyWrapper());
        return roleConvertMapper.rolesToRoleVOs(roles);
    }
}
