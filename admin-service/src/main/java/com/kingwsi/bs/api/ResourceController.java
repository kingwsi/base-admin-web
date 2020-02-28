package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.route.ResourceNode;
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

    @ApiOperation("list")
    @GetMapping("/route")
    public ResponseData<List<ResourceNode>> list() {
        return ResponseData.OK(resourceService.listRouteTree());
    }

    @ApiOperation("create")
    @PostMapping
    public ResponseData create(@RequestBody Resource resource) {
        resourceService.create(resource);
        return ResponseData.OK();
    }
}
