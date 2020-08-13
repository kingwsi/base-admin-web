package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceQuery;
import com.kingwsi.bs.entity.resource.ResourceVO;
import com.kingwsi.bs.service.ResourceService;
import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 路由
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("/list")
    public ResponseData list(HttpServletRequest request) {
        return ResponseData.OK(resourceService.list());
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("/routes")
    public ResponseData listRoutes() {
        return ResponseData.OK(resourceService.listByType(ResourceTypeEnum.MENU));
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

    @ApiOperation("更新资源")
    @DeleteMapping("/{id}")
    public ResponseData deleteById(@PathVariable String id) {
        return resourceService.deleteById(id) ? ResponseData.OK() : ResponseData.FAIL("删除失败");
    }
}
