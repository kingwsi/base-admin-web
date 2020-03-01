package com.kingwsi.bs.jwt;

import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;

/**
 * Description: jwt工具封装<br>
 * Comments Name: Token<br>
 * Date: 2019/8/2 11:42<br>
 * Author: wangshu<br>
 */
@Component
public class TokenUtil {
    public static final String KEY = "bf6a0773bd30c4a479c24ef6cfeb246e";

    private static AccessControlService service = null;

    public TokenUtil(AccessControlService accessControlService) {
        service = accessControlService;
    }

    /**
     * 解析TOKEN信息
     *
     * @return
     */
    public static UserVO getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
        String userName = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody().get("user").toString();
        return service.getUserWithRoleByName(userName);
    }
}
