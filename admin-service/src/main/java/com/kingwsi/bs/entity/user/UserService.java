package com.kingwsi.bs.entity.user;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * Description: []
 * Name: UserRepository
 * Author: wangshu
 * Date: 2019/6/29 15:52
 */
@Service
public interface UserService extends IService<User> {
    User getEffectiveUser(UserVO userVO);
}
