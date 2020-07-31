package com.kingwsi.bs.common.config;

import com.kingwsi.bs.jwt.JWTAuthenticationFilter;
import com.kingwsi.bs.service.impl.UserDetailServiceImpl;
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
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailServiceImpl userDetailService;

//    @Override
    protected void configure2(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth").permitAll()
                .antMatchers("/debug/**").permitAll()
                .antMatchers("/webjars/**","/swagger**/**","/v2/api-docs**","/h2-console/**").permitAll()
                .antMatchers("/**/*.gif", "/**/*.png", "/**/*.jpg", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico").permitAll()
                // 自定义资源过滤表达式
//                .antMatchers("/**").access("@customResourceFilterHandler.hasPermission(request ,authentication)")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .headers().frameOptions().disable().and();
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth").permitAll()
                .antMatchers("/debug/**").hasRole("admin")
                .antMatchers("/webjars/**","/swagger**/**","/v2/api-docs**","/h2-console/**").permitAll()
                .antMatchers("/**/*.gif", "/**/*.png", "/**/*.jpg", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico").permitAll()
//                .antMatchers("/**").access("@customResourceFilterHandler.hasPermission(request ,authentication)")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtLoginFilter("/api/auth", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

    }
}
