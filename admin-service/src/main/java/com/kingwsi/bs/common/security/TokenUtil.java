package com.kingwsi.bs.common.security;

import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.service.UserService;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Description: jwt工具封装<br>
 * Comments Name: Token<br>
 * Date: 2019/8/2 11:42<br>
 * Author: wangshu<br>
 */
@Component
@Slf4j
public class TokenUtil {
    public static final String KEY = "bf6a0773bd30c4a479c24ef6cfeb246e";

    private static AccessControlService service = null;

    private static UserService userService;

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static StringRedisTemplate stringRedisTemplate;

    private static RedisTemplate<String, Object> redisTemplate;

    public TokenUtil(AccessControlService accessControlService, UserService uService, RedisTemplate<String, Object> template) {
        userService = uService;
        service = accessControlService;
        redisTemplate = template;
    }

    /**
     * 解析TOKEN信息并获取当前用户完整信息（查询数据库）
     *
     * @return
     */
    public static UserVO getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        return getCurrentUser(servletRequestAttributes.getRequest());
    }

    /**
     * 解析TOKEN信息并获取当前用户完整信息
     * 根据token中标识插叙
     *
     * @return
     */
    public static UserVO getCurrentUser(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader("Authorization");
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
        String username = claims.getSubject();
        return service.getUserWithRoleByUsername(username);
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername() {
        String username = "unknown";
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert servletRequestAttributes != null;
            String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
            Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
            username = claims.getSubject();
        } catch (Exception e) {
            log.warn("Token解析用户信息失败->{}", e.getMessage());
        }
        return username;
    }

    /**
     * 获取用户id
     * @return
     */
    public static String getUserId() {
        String userId = "-1";
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert servletRequestAttributes != null;
            String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
            Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
            userId = claims.getId();
        } catch (Exception e) {
            log.warn("解析token获取userId失败->{}", e.getMessage());
        }
        return userId;
    }

    public static String createToken(String id, String username) {
//            redisTemplate.opsForValue().set(RedisKeyEnum.USER_AUTH_INFO + user.getId(), user,60, TimeUnit.SECONDS);
        return Jwts.builder()
                .setSubject(username)
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, TokenUtil.KEY)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public static UserVO parseToken(String tokenString) {
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(tokenString).getBody();
        String username = claims.getSubject();
        return service.getUserWithRoleByUsername(username);
    }

    /**
     * 检查token并解析数据
     *
     * @return
     */
    public static UserVO parseToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
        return parseToken(authorization);
    }

    public static Principal getPrincipal(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
        String userId = claims.get("user").toString();
        String requestURI = request.getRequestURI();
        // 获取当前请求url权限
        Principal principal = service.getPrincipal(userId);
        List<Resource> resources = service.listByUserAndMethod(userId, request.getMethod());
        Resource resultResource = null;
        for (Resource resource : resources) {
            if (antPathMatcher.match(resource.getUri(), requestURI)) {
                resultResource = resource;
            }
        }
        if (resultResource == null) {
            return null;
        }
        principal.setMethod(request.getMethod());
        principal.setUri(requestURI);
//        principal.setFilterRule(resultResource.getFilterRule());
        return principal;
    }
}
