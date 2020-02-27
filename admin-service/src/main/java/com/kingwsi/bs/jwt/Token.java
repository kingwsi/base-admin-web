package com.kingwsi.bs.jwt;

import com.kingwsi.bs.entity.permission.Permission;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Description: jwt工具封装<br>
 * Comments Name: Token<br>
 * Date: 2019/8/2 11:42<br>
 * Author: wangshu<br>
 */
public class Token {
    private static final String KEY = "bf6a0773bd30c4a479c24ef6cfeb246e";

    @Autowired
    private AccessControlService accessControlService;

    private static AccessControlService service = null;

    @PostConstruct
    void init() {
        service = this.accessControlService;
    }

    /**
     * 解析TOKEN信息
     *
     * @return
     */
    public static UserVO getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Optional.ofNullable(request.getHeader("Authorization"))
                .map(token -> Jwts.parser()
                        .setSigningKey(KEY)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody())
                .map(claims -> {
                    List roleByUser = claims.get("role", List.class);
                    Role requiredRole = service.getRequiredRoleByPermission(new Permission(request.getMethod(), request.getRequestURI()));
                    if (roleByUser.contains(requiredRole.getName())) {
                        return new UsernamePasswordAuthenticationToken(claims.get("username"), null, Collections.emptyList());
                    }
                    return null;
                }).orElse(null);
        // TODO 从token中拿到当前用户信息
        String token = request.getHeader("Authorization");
        UserVO user = Optional.ofNullable(token)
                .map(t -> Jwts.parser()
                        .setSigningKey(KEY)
                        .parseClaimsJws(token)
                        .getBody()).map(claims -> {
                    UserVO userVO = new UserVO();
                    return userVO;
                }).orElse(null);
        if (user == null) {
            throw new RuntimeException("身份校验失败！");
        }
        return user;
    }

    // DEBUG
    public static String generateToken(UserVO vo) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }
}
