package com.kingwsi.bs.entity.resource;

import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import lombok.Data;

@Data
public class ResourceQuery {
    private String name;
    private ResourceTypeEnum type;
}
