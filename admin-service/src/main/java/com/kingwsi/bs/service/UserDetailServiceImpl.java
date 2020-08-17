package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.mapper.UserMapper;
import com.kingwsi.bs.service.auth.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * description: UserDetailServiceImpl <br>
 * date: 2020/7/30 15:08 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    public UserDetailServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new UserDetailsImpl(user);
    }
}
