package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.UserApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 授权相关接口
 * Name: AuthController
 * Author: wangshu
 * Date: 2019/6/29 15:15
 */
@Api(tags = "授权相关接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserApplicationService userApplicationService;

    @ApiOperation("创建令牌")
    @PostMapping
    public ResponseEntity<String> auth(@RequestBody UserVO vo)
    {

        return ResponseEntity.ok(userApplicationService.createToken(vo));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping
    public ResponseEntity<UserVO> getCurrentUser(HttpServletRequest request) {
        return ResponseEntity.ok(userApplicationService.getCurrentUser(request));
    }
}
