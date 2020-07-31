package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.role.RoleVO;
import com.kingwsi.bs.service.RoleService;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "角色")
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取角色列表")
    @GetMapping
    public ResponseData listRoles() {
        return ResponseData.OK(roleService.listRoles());
    }

    @PostMapping
    public ResponseData create(@RequestBody RoleVO roleVO) {
        roleService.createRoleVO(roleVO);
        return ResponseData.OK();
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable("id") String id) {
        roleService.deleteById(id);
        return ResponseData.OK();
    }

    @PutMapping("/{id}")
    public ResponseData update(@PathVariable("id") String id, @RequestBody RoleVO roleVO) {
        roleVO.setId(id);
        roleService.updateById(roleVO);
        return ResponseData.OK();
    }

    @GetMapping("/page")
    public ResponseData page(Page<Role> page, RoleVO vo) {
        return ResponseData.OK(roleService.listOfPages(page, vo));
    }
}
