package com.kingwsi.bs.entity.resource;

import com.kingwsi.bs.entity.role.RoleVO;
import lombok.Data;

import java.util.List;

@Data
public class ResourceVO {
    private String id;
    private String name;
    private String description;
    private String path;
    private List<String> resourceIds;

    public RoleVO toRoleVO() {
        return new RoleVO(this.id, this.name, this.description, this.resourceIds);
    }
}
