package com.kingwsi.bs.common.config;

import com.kingwsi.bs.jwt.JWTAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth").permitAll()
                .antMatchers("/debug/**").permitAll()
                .antMatchers("/webjars/**","/swagger**/**","/v2/api-docs**","/h2-console/**").permitAll()
                .antMatchers("/**/*.gif", "/**/*.png", "/**/*.jpg", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.ico").permitAll()
                // 自定义资源过滤表达式
//                .antMatchers("/**").access("@customResourceFilterHandler.hasPermission(request ,authentication)")
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
}
