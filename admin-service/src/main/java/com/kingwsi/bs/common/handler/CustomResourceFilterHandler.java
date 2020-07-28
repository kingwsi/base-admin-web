package com.kingwsi.bs.common.handler;

import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.service.AccessControlService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 自定义资源过滤
 * Name: CorsConfig
 * Author: wangshu
 * Date: 2020/04/17 18:34
 */
@Component
public class CustomResourceFilterHandler {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final AccessControlService accessControlService;

    public CustomResourceFilterHandler(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Principal) {
        }
        return false;
    }
}
