package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.UserApplicationService;
import com.kingwsi.bs.util.bean.ResponseData;
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
    public ResponseData<String> auth(@RequestBody UserVO vo)
    {
        return ResponseData.OK(userApplicationService.createToken(vo));
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ResponseData<String> logout()
    {
        return ResponseData.OK();
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public ResponseData<UserVO> getCurrentUser(HttpServletRequest request) {
        return ResponseData.OK(userApplicationService.getCurrentUser(request));
    }
}
