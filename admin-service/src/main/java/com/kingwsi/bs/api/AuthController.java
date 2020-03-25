package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.jwt.TokenUtil;
import com.kingwsi.bs.util.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @ApiOperation("创建令牌")
    @PostMapping
    public ResponseData auth(@RequestBody @Valid UserVO vo) {
        return ResponseData.OK(TokenUtil.createToken(vo));
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ResponseData logout() {
        return ResponseData.OK();
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public ResponseData getCurrentUser() {
        return ResponseData.OK(TokenUtil.getCurrentUser());
    }
}
