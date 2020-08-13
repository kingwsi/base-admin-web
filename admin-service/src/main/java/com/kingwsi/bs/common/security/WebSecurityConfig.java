package com.kingwsi.bs.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingwsi.bs.service.UserDetailServiceImpl;
import com.kingwsi.bs.common.bean.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * Description: security配置
 * Name: WebSecurityConfig
 * Author: wangshu
 * Date: 2019/6/29 17:18
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .antMatchers("/debug/**").permitAll()
                .antMatchers("/webjars/**", "/swagger**/**", "/v2/api-docs**", "/h2-console/**").permitAll()
                .antMatchers("/**/*.gif", "/**/*.png", "/**/*.jpg", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico").permitAll()
//                .antMatchers("/**").access("@customResourceFilterHandler.hasPermission(request ,authentication)")
                .anyRequest().authenticated()
                // 解决页面frame嵌套报错问题
                .and().headers().frameOptions().disable()
                .and().addFilterBefore(new JwtLoginFilter("/api/auth", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            resp.setStatus(401);
                            PrintWriter out = resp.getWriter();
                            out.write(new ObjectMapper().writeValueAsString(ResponseData.FAIL("用户未登录", 401)));
                            out.flush();
                            out.close();
                        }
                );

    }
}
