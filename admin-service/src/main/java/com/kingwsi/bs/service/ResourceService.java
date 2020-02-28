package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.route.ResourceNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    public ResourceService(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    public void create(Resource resource) {
        this.resourceRepository.save(resource);
    }

    public List<ResourceNode> listRouteTree() {
        List<ResourceNode> resources = this.resourceMapper.selectAllByType(ResourceTypeEnum.ROUTE);
        ResourceTree resourceTree = new ResourceTree(resources);
        return resourceTree.buildTree();
    }
}
