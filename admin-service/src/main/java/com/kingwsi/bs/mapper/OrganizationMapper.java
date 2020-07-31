package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingwsi.bs.entity.organization.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrganizationMapper extends BaseMapper<Organization> {
}
