package com.kingwsi.bs.jwt;

import com.kingwsi.bs.common.enumerate.RedisKeyEnum;
import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.entity.login.AuthenticationVO;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.service.UserService;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.exception.CustomException;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.objects.NativeString;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * 解析TOKEN信息并获取当前用户完整信息（查询数据库）
     *
     * @return
     */
    public static UserVO getCurrentUser(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader("Authorization");
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
        String userId = claims.get("user").toString();
        return service.getUserWithRoleById(userId);
    }

    public static String createToken(AuthenticationVO vo) {
        User user = userService.getEffectiveUser(vo);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", user.getId());
            map.put("username", user.getUsername());
            map.put("roles", service.getRoleIdsByUserId(user.getId()));
//            redisTemplate.opsForValue().set(RedisKeyEnum.USER_AUTH_INFO + user.getId(), user,60, TimeUnit.SECONDS);
            return Jwts.builder()
                    .setClaims(map)
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, TokenUtil.KEY)
                    .compact();
        }
        throw new CustomException("密码错误或账号不存在！");
    }

    @SuppressWarnings("unchecked")
    public static UserVO parseToken(String tokenString) {
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(tokenString).getBody();
        UserVO userVO = new UserVO();
        userVO.setId(claims.get("user").toString());
        userVO.setUsername(claims.get("username").toString());
        userVO.setRoles((List<String>) claims.get("roles"));
        return userVO;
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
        principal.setFilterRule(resultResource.getFilterRule());
        return principal;
    }
}
