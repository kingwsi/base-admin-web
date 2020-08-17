package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.common.security.TokenUtil;
import com.kingwsi.bs.service.UserService;
import com.kingwsi.bs.service.UserDetailServiceImpl;
import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户相关接口
 * Name: UserController
 * Author: wangshu
 * Date: 2019/6/29 23:33
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final UserDetailServiceImpl userDetailService;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public UserController(UserService userService, UserDetailServiceImpl userDetailService, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @ApiOperation("创建用户")
    @PostMapping
    public ResponseData createUser(@Validated @RequestBody UserVO user) {
        userService.createUser(user);
        return ResponseData.OK();
    }

    @ApiOperation("更新用户")
    @PutMapping
    public ResponseData update(@Validated(Update.class) @RequestBody UserVO userVO) {
        userService.updateUser(userVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取用户分页")
    @GetMapping("/page")
    public ResponseData page(Page<UserVO> page, UserVO userVO) {
        return ResponseData.OK(userService.listUsersOfPage(page, userVO));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public ResponseData getUserInfo(HttpServletRequest httpServletRequest) {
        return ResponseData.OK(TokenUtil.getCurrentUser(httpServletRequest));
    }

    @GetMapping("/apis")
    public ResponseEntity<List> getAllApi() {
        List<HashMap<String, String>> urlList = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                hashMap.put("url", url);
            }
            hashMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            hashMap.put("methodName", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String type = methodsCondition.toString();
            if (type.startsWith("[") && type.endsWith("]")) {
                type = type.substring(1, type.length() - 1);
                hashMap.put("method", type); // 方法名
            }
            urlList.add(hashMap);
        }
        return ResponseEntity.ok(urlList);
    }

}
