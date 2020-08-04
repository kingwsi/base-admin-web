package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kingwsi.bs.entity.login.AuthenticationVO;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserVO;
import org.springframework.stereotype.Service;

/**
 * Description: []
 * Name: UserRepository
 * Author: wangshu
 * Date: 2019/6/29 15:52
 */
public interface UserService extends IService<User> {
    void updateUser(UserVO userVO);

    void createUser(UserVO vo);

    IPage<UserVO> listUsersOfPage(Page<User> page, UserVO userVO);
}
