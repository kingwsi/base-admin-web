package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.jwt.TokenUtil;
import com.kingwsi.bs.mapper.ResourceMapper;
import com.kingwsi.bs.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    private final ResourceMapper resourceMapper;

    private final ResourceConvertMapper resourceConvertMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public ResourceServiceImpl(ResourceMapper resourceMapper, ResourceConvertMapper resourceConvertMapper) {
        this.resourceMapper = resourceMapper;
        this.resourceConvertMapper = resourceConvertMapper;
    }

    public void create(RouteVO vo) {
        Resource resource = resourceConvertMapper.routeToResource(vo);
        this.resourceMapper.insert(resource);
    }

    public List<ResourceNode> listRouteTree() {
        List<ResourceNode> resources = this.resourceMapper.selectAllByType(ResourceTypeEnum.ROUTE);
        ResourceTree resourceTree = new ResourceTree(resources);
        return resourceTree.buildTree();
    }

    public List<Resource> listRoute() {
        UserVO currentUser = TokenUtil.getCurrentUser();
        return resourceMapper.selectRouteByUserId(currentUser.getId());
    }

    @Override
    public List<Resource> listByUserId(String userId) {
        return resourceMapper.selectByUserId(userId);
    }
}
