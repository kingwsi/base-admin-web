package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.organization.Organization;
import com.kingwsi.bs.mapper.OrganizationMapper;
import com.kingwsi.bs.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    private final OrganizationMapper organizationMapper;

    private List<Organization> organizations;

    public OrganizationServiceImpl(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    public List<Organization> listChildByParentId(String parentId) {
        List<Organization> list = this.list();
        this.organizations = new ArrayList<>();
        List<Organization> recursive = recursive(list, parentId);
        return recursive;
    }

    @Override
    public List<Organization> listChildById(String id) {
        List<Organization> list = this.list();
        Organization organization = this.organizationMapper.selectById(id);
        this.organizations = new ArrayList<>();
        List<Organization> result = recursive(list, organization.getParentId());
        result.add(organization);
        return result;
    }

    public List<Organization> recursive(List<Organization> organizations, String parentId) {
        List<Organization> result = organizations.stream().filter(o -> o.getParentId().equals(parentId)).collect(Collectors.toList());
        if (!result.isEmpty()){
            this.organizations.addAll(result);
            for (Organization o : result) {
                recursive(organizations, o.getId());
            }
        }
        return this.organizations;
    }
}
