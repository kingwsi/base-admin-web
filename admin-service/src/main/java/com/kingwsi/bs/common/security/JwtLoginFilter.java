package com.kingwsi.bs.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.common.bean.ResponseData;
import com.kingwsi.bs.service.auth.UserDetailsImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * description: JwtLoginFilter <br>
 * date: 2020/7/30 14:24 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    /**
     * 登陆成功处理,返回token
     *
     * @param request
     * @param resp
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();//获取登录用户的角色
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getId();
        String token = TokenUtil.createToken(userId, authResult.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("data", token);
        map.put("code", 200);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }

    /**
     * 登录失败处理
     *
     * @param req
     * @param resp
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        ResponseData responseData = ResponseData.FAIL();
        if (failed instanceof LockedException) {
            responseData.setMessage("账户被锁定，请联系管理员!");
        } else if (failed instanceof CredentialsExpiredException) {
            responseData.setMessage("密码过期，请联系管理员!");
        } else if (failed instanceof AccountExpiredException) {
            responseData.setMessage("账户过期，请联系管理员!");
        } else if (failed instanceof DisabledException) {
            responseData.setMessage("账户被禁用，请联系管理员!");
        } else if (failed instanceof BadCredentialsException) {
            responseData.setMessage("用户名或者密码输入错误，请重新输入!");
        }
        out.write(new ObjectMapper().writeValueAsString(responseData));
        out.flush();
        out.close();
    }
}