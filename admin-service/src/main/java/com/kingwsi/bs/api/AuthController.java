package com.kingwsi.bs.api;

import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

//    @ApiOperation("创建令牌")
//    @PostMapping
//    public ResponseData auth(@RequestBody @Valid AuthenticationVO vo) {
//        return ResponseData.OK(TokenUtil.createToken(vo));
//    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ResponseData logout() {
        return ResponseData.OK();
    }
}
