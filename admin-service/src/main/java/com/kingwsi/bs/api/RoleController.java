package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.service.RoleService;
import com.kingwsi.bs.util.bean.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseData<Iterable<Role>> listRoles() {
        return ResponseData.OK(roleService.listRoles());
    }
}
