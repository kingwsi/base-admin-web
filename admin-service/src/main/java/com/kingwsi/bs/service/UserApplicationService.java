package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.user.*;
import com.kingwsi.bs.mapper.UsersAndRolesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Description: []
 * Name: UserApplicationService
 * Author: wangshu
 * Date: 2019/6/29 23:18
 */
@Service
public class UserApplicationService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    private final UsersAndRolesMapper usersAndRolesMapper;

    public UserApplicationService(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, UsersAndRolesMapper usersAndRolesMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.usersAndRolesMapper = usersAndRolesMapper;
    }

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userService.save(user);
        return user;
    }
}
