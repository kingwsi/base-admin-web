package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceQuery;
import com.kingwsi.bs.entity.resource.RouteVO;
import com.kingwsi.bs.service.ResourceService;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/routes")
    public ResponseData listRoute() {
        return ResponseData.OK(resourceService.listRoute());
    }

    @ApiOperation("获取路由list")
    @GetMapping("/apis")
    public ResponseData listApis() {
        return ResponseData.OK(resourceService.listByType(ResourceTypeEnum.ROUTE));
    }

    @ApiOperation("创建资源")
    @PostMapping
    public ResponseData create(@RequestBody RouteVO routeVO) {
        resourceService.create(routeVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取资源分页")
    @GetMapping("/page")
    public ResponseData page(Page page, ResourceQuery resourceVO) {
        return ResponseData.OK(resourceService.listOfPage(page, resourceVO));
    }
}
