package com.kingwsi.bs.common.security;

/**
 * description: JwtFilter <br>
 * date: 2020/7/30 14:23 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingwsi.bs.entity.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String jwtToken = req.getHeader("authorization");
        if (!StringUtils.isEmpty(jwtToken)) {
            try {
                UserVO userVO = TokenUtil.parseToken(jwtToken.replace("Bearer", ""));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userVO, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (Exception e) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                log.warn("Token解析失败");
                Map<String, String> map = new HashMap<>();
                map.put("data", "身份验证失败");
                map.put("code", "401");
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(401);
                PrintWriter out = servletResponse.getWriter();
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}