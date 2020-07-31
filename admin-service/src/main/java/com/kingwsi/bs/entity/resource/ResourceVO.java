package com.kingwsi.bs.entity.resource;

import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.role.RoleVO;
import lombok.Data;

import java.util.List;

@Data
public class ResourceVO {
    private String id;
    private String name;
    private ResourceTypeEnum type;
    private String description;
    private String uri;
    private String method;
    private String parentId;
    private String sort;
    private String component;
    private String icon;
    private List<String> resourceIds;

    public RoleVO toRoleVO() {
        return new RoleVO(this.id, this.name, this.description, this.resourceIds);
    }
}
