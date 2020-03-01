package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.resource.ResourceNode;
import com.kingwsi.bs.service.ResourceService;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: [路由]
 * Name: RouteController
 * Author: wangshu
 * Date: 2020/2/29 13:28
 */
@Api(tags = "路由接口")
@RestController
@RequestMapping("/api/routes")
public class RouteController {
    private final ResourceService resourceService;

    public RouteController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiOperation("获取当前用户角色下路由")
    @GetMapping
    public ResponseData<List<ResourceNode>> getRouteTreeByRole(){
        return ResponseData.OK(resourceService.listRouteTreeByRole());
    }
}
