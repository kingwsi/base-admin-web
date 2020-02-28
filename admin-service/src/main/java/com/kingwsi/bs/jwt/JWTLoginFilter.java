package com.kingwsi.bs.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: [默认授权请求处理，通过校验后保存token到Response Headers]
 * Name: JWTAuthenticationFilter
 * Author: wangshu
 * Date: 2019/6/29 16:56
 */

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private AccessControlService accessControlService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, AccessControlService accessControlService) {
        this.authenticationManager = authenticationManager;
        this.accessControlService = accessControlService;
        // 默认授权请求路径
//        super.setFilterProcessesUrl("/api/auth");
    }

    /**
     * 用户登录校验
     *
     * @param req
     * @param res
     * @return
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            User user = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 身份校验通过后处理，生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Map<String, Object> map = new HashMap<String, Object>();
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        map.put("role", accessControlService.listRoleByUserName(username));
        map.put("username",username);
        String token = Jwts.builder()
                .setSubject(username)
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        res.addHeader("Authorization", "Bearer " + token);
    }

}
