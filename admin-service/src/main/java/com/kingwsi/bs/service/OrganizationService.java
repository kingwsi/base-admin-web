package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.organization.Organization;
import com.kingwsi.bs.mapper.OrganizationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationMapper organizationMapper;

    private List<Organization> organizations;

    public OrganizationService(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
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
