package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.organization.Organization;
import com.kingwsi.bs.mapper.OrganizationMapper;
import com.kingwsi.bs.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

}
