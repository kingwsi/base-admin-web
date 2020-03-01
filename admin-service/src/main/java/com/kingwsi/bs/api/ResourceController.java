package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.resource.ResourceNode;
import com.kingwsi.bs.service.ResourceService;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 路由
 */
@Api(tags = "路由")
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiOperation("获取路由list")
    @GetMapping("/tree/routes")
    public ResponseData<List<ResourceNode>> listTreeRoute() {
        return ResponseData.OK(resourceService.listRouteTree());
    }

    @ApiOperation("获取路由list")
    @GetMapping("/routes")
    public ResponseData<List<ResourceNode>> listRoute() {
        return ResponseData.OK(resourceService.listRoute());
    }

    @ApiOperation("create")
    @PostMapping
    public ResponseData create(@RequestBody Resource resource) {
        resourceService.create(resource);
        return ResponseData.OK();
    }
}
