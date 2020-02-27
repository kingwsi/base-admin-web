package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.role.RoleVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/roles")
public class RoleController {
    public ResponseEntity<RoleVO> listRoles() {
        return ResponseEntity.ok(null);
    }
}
