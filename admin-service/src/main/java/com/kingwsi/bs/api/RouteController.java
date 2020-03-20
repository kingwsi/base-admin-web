package com.kingwsi.bs.api;

import com.kingwsi.bs.service.ResourceService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
