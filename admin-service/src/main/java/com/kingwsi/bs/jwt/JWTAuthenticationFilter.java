package com.kingwsi.bs.jwt;

import com.alibaba.fastjson.JSON;
import com.kingwsi.bs.entity.authority.Principal;
import com.kingwsi.bs.util.bean.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Description: token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * Name: JWTAuthenticationFilter
 * Author: wangshu
 * Date: 2019/6/29 23:26
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 拦截请求，获取请求头信息，校验token合法性
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
//        if (!StringUtils.isEmpty(request.getHeader("Authorization"))) {
//            // 在此处将用户信息存入上下文
//            Principal principal = TokenUtil.getPrincipal(request);
//            if (principal == null) {
//                response.setCharacterEncoding("utf-8");
//                response.setContentType("application/json; charset=utf-8");
//                response.setStatus(403);
//                response.getWriter().write(JSON.toJSONString(ResponseData.FAIL("无权限",403)));
//                log.info("无权限");
//                return;
//            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
        chain.doFilter(request, response);
    }
}
