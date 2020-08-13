package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RoleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper extends BaseMapper<Role> {
    IPage<RoleVO> selectPageWithResources(Page<RoleVO> page, @Param("roleVO") RoleVO roleVO);

    RoleVO selectRoleWithResourceIds(@Param("id") String id);

    RoleVO selectRoleWithResources(@Param("id") String id);

    List<Role> selectByUserId(@Param("userId") String userId);
}
