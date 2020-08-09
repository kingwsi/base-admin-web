package com.kingwsi.bs.entity.resource;

import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
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
    private String remark;
    private List<String> resourceIds;

}
