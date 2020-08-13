package com.kingwsi.bs.api;

import com.kingwsi.bs.service.OrganizationService;
import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "资源管理")
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperation("获取组织list")
    @GetMapping
    public ResponseData list(){
        return ResponseData.OK();
    }
}
