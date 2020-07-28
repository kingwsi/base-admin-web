package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingwsi.bs.entity.organization.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService extends IService<Organization> {

    /**
     * 获取子组织列表，按父级id获取
     * @param parentId 父级id
     * @return
     */
    List<Organization> listChildByParentId(String parentId);

    /**
     * 获取子组织列表，按组织id获取
     * @param id id
     * @return
     */
    List<Organization> listChildById(String id);
}
