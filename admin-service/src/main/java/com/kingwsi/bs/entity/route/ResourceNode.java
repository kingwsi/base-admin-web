package com.kingwsi.bs.entity.route;

import com.kingwsi.bs.entity.resource.ResourceTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ResourceNode {
    private String id;
    private String name;
    private ResourceTypeEnum type;
    private String uri;
    private String method;
    private String parentId;
    private Integer sort;
    List<ResourceNode> children;
}
