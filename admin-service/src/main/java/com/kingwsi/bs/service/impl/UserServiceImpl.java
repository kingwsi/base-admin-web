package com.kingwsi.bs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserConvertMapper;
import com.kingwsi.bs.exception.CustomException;
import com.kingwsi.bs.mapper.UserMapper;
import com.kingwsi.bs.mapper.UsersAndRolesMapper;
import com.kingwsi.bs.service.UserService;
import com.kingwsi.bs.entity.user.UserVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserConvertMapper userConvertMapper;

    private final UsersAndRolesMapper usersAndRolesMapper;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserConvertMapper userConvertMapper, UsersAndRolesMapper usersAndRolesMapper) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConvertMapper = userConvertMapper;
        this.usersAndRolesMapper = usersAndRolesMapper;
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

    @Override
    public void createUser(UserVO vo) {
        User user = userConvertMapper.toUser(vo);
        user.setPassword(bCryptPasswordEncoder.encode(vo.getPassword()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",vo.getUsername());
        if (!userMapper.selectList(queryWrapper).isEmpty()) throw new CustomException("用户名已存在");
        userMapper.insert(user);
        usersAndRolesMapper.batchInsert(user.getId(), vo.getRoles());
    }

    @Override
    public IPage<UserVO> listUsersOfPage(Page<User> page) {
        page.setSearchCount(true);
        return userMapper.listUsersOfPage(page);
    }

    @Override
    public void updateUser(UserVO userVO) {
        userVO.setPassword(null);
        usersAndRolesMapper.deleteByUserId(userVO.getId());
        User user = userConvertMapper.toUser(userVO);
        usersAndRolesMapper.batchInsert(user.getId(), userVO.getRoles());
        this.updateById(user);
    }
}
