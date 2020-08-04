package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceQuery;
import com.kingwsi.bs.entity.resource.ResourceVO;
import com.kingwsi.bs.service.ResourceService;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
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

    @ApiOperation("获取菜单列表")
    @GetMapping("/list")
    public ResponseData list(ResourceTypeEnum type) {
        if (type == null) {
            type = ResourceTypeEnum.MENU;
        }
        return ResponseData.OK(resourceService.listByType(type));
    }

    @ApiOperation("创建资源")
    @PostMapping
    public ResponseData create(@RequestBody ResourceVO resourceVO) {
        resourceService.create(resourceVO);
        return ResponseData.OK();
    }

    @ApiOperation("更新资源")
    @PutMapping
    public ResponseData updateById(@RequestBody ResourceVO resourceVO) {
        if (StringUtils.isEmpty(resourceVO.getId())){
            return ResponseData.FAIL("id不能为空");
        }
        resourceService.updateById(resourceVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取资源分页")
    @GetMapping("/page")
    public ResponseData page(Page page, ResourceQuery resourceVO) {
        return ResponseData.OK(resourceService.listOfPage(page, resourceVO));
    }
}
