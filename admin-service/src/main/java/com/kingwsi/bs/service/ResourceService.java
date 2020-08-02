package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService extends IService<Resource> {

    void create(RouteVO vo);

    List<Resource> listRoute();

    List<Resource> listByUserId(String id);

    List<Resource> listByMethodAndUserId(String resourceTypeEnum, String userId, String uri);

    List<ResourceVO> listByType(ResourceTypeEnum route);

    IPage<ResourceVO> listOfPage(Page page, ResourceQuery resourceVO);
}
