package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.exception.CustomException;
import com.kingwsi.bs.jwt.TokenUtil;
import com.kingwsi.bs.mapper.ResourceMapper;
import com.kingwsi.bs.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    private final ResourceMapper resourceMapper;

    private final ResourceConvertMapper resourceConvertMapper;

    public ResourceServiceImpl(ResourceMapper resourceMapper, ResourceConvertMapper resourceConvertMapper) {
        this.resourceMapper = resourceMapper;
        this.resourceConvertMapper = resourceConvertMapper;
    }

    public void create(ResourceVO vo) {
        if (checkRepeat(vo)) {
            throw new CustomException("资源重复");
        }
        Resource resource = resourceConvertMapper.resourceVOToResource(vo);
        this.resourceMapper.insert(resource);
    }

    public int updateById(ResourceVO vo) {
        if (checkRepeat(vo)) {
            throw new CustomException("资源重复");
        }
        Resource resource = resourceConvertMapper.resourceVOToResource(vo);
        return this.resourceMapper.updateById(resource);
    }

    public List<Resource> listRoute() {
        UserVO currentUser = TokenUtil.getCurrentUser();
        return resourceMapper.selectRouteByUserId(currentUser.getId());
    }

    @Override
    public List<Resource> listByUserId(String userId) {
        return resourceMapper.selectByUserId(userId);
    }

    @Override
    public List<Resource> listByMethodAndUserId(String method, String userId, String uri) {
        return resourceMapper.selectByMethodAndUserIdAndUri(method, userId, uri);
    }

    @Override
    public List<ResourceVO> listByType(ResourceTypeEnum route) {
        return resourceMapper.selectAllByType(route);
    }

    @Override
    public IPage<ResourceVO> listOfPage(Page page, ResourceQuery resourceVO) {
        return resourceMapper.selectOfPage(page, resourceVO);
    }

    /**
     * 检查是否重复
     * 规则：uri唯一，MENU类型名称唯一
     *
     * @param resourceVO
     * @return true 重复 false 不重复
     */
    private boolean checkRepeat(ResourceVO resourceVO) {
        int repeatCount = resourceMapper.countRepeat(resourceVO);
        return repeatCount > 0;
    }
}
