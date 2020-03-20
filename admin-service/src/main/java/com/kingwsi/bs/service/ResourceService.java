package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingwsi.bs.entity.resource.*;
import com.kingwsi.bs.entity.resource.ResourceNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService extends IService<Resource> {

    void create(Resource resource);

    List<ResourceNode> listRouteTree();

    List<Resource> listRoute();
}
