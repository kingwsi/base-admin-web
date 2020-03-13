package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingwsi.bs.entity.role.*;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Description: 角色服务<br>
 * Comments Name: RoleService<br>
 * Date: 2019/7/11 16:52<br>
 * Author: Administrator<br>
 */
public interface RoleService extends IService<Role> {

    List<RoleVO> listRoles();

    void createRoleVO(RoleVO roleVO);

    void deleteById(String id);

    void updateById(RoleVO roleVO);
}
