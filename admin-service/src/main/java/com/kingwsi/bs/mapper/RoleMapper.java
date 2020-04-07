package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RoleVO;
import org.springframework.stereotype.Component;

@Component
public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> selectWithResourcesPage(Page<Role> page, RoleVO roleVO);
}
