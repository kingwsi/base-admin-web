package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.jwt.TokenUtil;
import com.kingwsi.bs.mapper.ResourceMapper;
import com.kingwsi.bs.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public void create(Resource resource) {
        this.resourceMapper.insert(resource);
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
