package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserConvertMapper;
import com.kingwsi.bs.entity.user.UserVO;
import com.kingwsi.bs.common.exception.CustomException;
import com.kingwsi.bs.mapper.UserMapper;
import com.kingwsi.bs.mapper.UsersAndRolesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description: []
 * Name: UserRepository
 * Author: wangshu
 * Date: 2019/6/29 15:52
 */
@Service
@Transactional(rollbackForClassName = {"Exception"})
public class UserService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserConvertMapper userConvertMapper;

    private final UsersAndRolesMapper usersAndRolesMapper;

    public UserService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder, UserConvertMapper userConvertMapper, UsersAndRolesMapper usersAndRolesMapper) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConvertMapper = userConvertMapper;
        this.usersAndRolesMapper = usersAndRolesMapper;
    }

    public void createUser(UserVO vo) {
        User user = userConvertMapper.toUser(vo);
        user.setPassword(bCryptPasswordEncoder.encode(vo.getPassword()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", vo.getUsername());
        if (!userMapper.selectList(queryWrapper).isEmpty()) throw new CustomException("用户名已存在");
        userMapper.insert(user);
        usersAndRolesMapper.batchInsert(user.getId(), vo.getRoles());
    }

    public IPage<UserVO> listUsersOfPage(Page<UserVO> page, UserVO userVO) {
        return userMapper.listUsersOfPage(page, userVO);
    }

    /**
     * 更新用户
     * 用户名称不可修改
     * @param userVO
     */
    public void updateUser(UserVO userVO) {
        userVO.setPassword(null);
        usersAndRolesMapper.deleteByUserId(userVO.getId());
        User user = userConvertMapper.toUser(userVO);
        usersAndRolesMapper.batchInsert(user.getId(), userVO.getRoles());
        userMapper.updateById(user);
    }

    public UserVO getUserById(String id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new CustomException("用户不存在");
        }
        return userConvertMapper.toVO(user);
    }

    public void getUserByUsername(String username) {

    }
}
