package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingwsi.bs.entity.organization.Organization;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
}
