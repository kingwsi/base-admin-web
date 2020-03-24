package com.kingwsi.bs.jwt;

import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.service.UserService;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.exception.CustomException;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

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

    public TokenUtil(AccessControlService accessControlService, UserService uService) {
        userService = uService;
        service = accessControlService;
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

    public static String createToken(UserVO vo) {
        User user = userService.getEffectiveUser(vo);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", user.getId());
            map.put("username", user.getUsername());
            map.put("roles", service.getRolesByUserId(user.getId()));
            return Jwts.builder()
                    .setClaims(map)
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, TokenUtil.KEY)
                    .compact();
        }
        throw new CustomException("密码错误或账号不存在！");
    }

    public static UserVO checkToken(String tokenString) {
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(tokenString).getBody();
        UserVO userVO = new UserVO();
        userVO.setId(claims.get("user").toString());
        userVO.setUsername(claims.get("username").toString());
        return userVO;
    }

    public static UserVO checkToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        String authorization = servletRequestAttributes.getRequest().getHeader("Authorization");
        Claims claims = Jwts.parser().setSigningKey(TokenUtil.KEY).parseClaimsJws(authorization).getBody();
        UserVO userVO = new UserVO();
        userVO.setId(claims.get("userId").toString());
        userVO.setUsername(claims.get("userName").toString());
        return userVO;
    }
}
