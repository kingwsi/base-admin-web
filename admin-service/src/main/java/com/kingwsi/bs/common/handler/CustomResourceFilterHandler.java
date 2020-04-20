package com.kingwsi.bs.common.handler;

import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.ResourceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 自定义资源过滤
 * Name: CorsConfig
 * Author: wangshu
 * Date: 2020/04/17 18:34
 */
@Component
public class CustomResourceFilterHandler {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final ResourceService resourceService;

    public CustomResourceFilterHandler(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean hasPermission = false;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserVO) {
            List<Resource> resources = resourceService.listByMethodAndUserId(request.getMethod(), ((UserVO) principal).getId());
            for(Resource resource : resources){
                if (antPathMatcher.match(resource.getUri(), request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
