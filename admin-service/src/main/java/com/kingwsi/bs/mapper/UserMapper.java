package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVO> listUsersOfPage(Page<UserVO> page, @Param("user") UserVO userVO);
}
