package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.resource.ResourceNode;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.entity.user.UsersAndRolesMapper;
import com.kingwsi.bs.jwt.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    private final UsersAndRolesMapper usersAndRolesMapper;

    public ResourceService(ResourceRepository resourceRepository, ResourceMapper resourceMapper, UsersAndRolesMapper usersAndRolesMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
        this.usersAndRolesMapper = usersAndRolesMapper;
    }

    public void create(Resource resource) {
        this.resourceRepository.save(resource);
    }

    public List<ResourceNode> listRouteTree() {
        List<ResourceNode> resources = this.resourceMapper.selectAllByType(ResourceTypeEnum.ROUTE);
        ResourceTree resourceTree = new ResourceTree(resources);
        return resourceTree.buildTree();
    }

    public List<ResourceNode> listRouteTreeByRole() {
        UserVO currentUser = TokenUtil.getCurrentUser();
        List<ResourceNode> resourceNodes = resourceMapper.selectByUserId(currentUser.getId());
        ResourceTree resourceTree = new ResourceTree(resourceNodes);
        return resourceTree.buildTree();
    }

    public List<ResourceNode> listRoute() {
        UserVO currentUser = TokenUtil.getCurrentUser();
        return resourceMapper.selectByUserId(currentUser.getId());
    }
}
