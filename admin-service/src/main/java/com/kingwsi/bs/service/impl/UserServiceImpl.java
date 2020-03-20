package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.mapper.UserMapper;
import com.kingwsi.bs.entity.user.UserService;
import com.kingwsi.bs.entity.user.UserVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getEffectiveUser(UserVO userVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userVO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && bCryptPasswordEncoder.matches(userVO.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }
}
