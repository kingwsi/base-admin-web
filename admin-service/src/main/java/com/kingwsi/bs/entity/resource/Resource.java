package com.kingwsi.bs.entity.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 资源<br>
 * Comments Name: Resource<br>
 * Date: 2019/7/11 15:45<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_resources")
public class Resource extends BaseEntity {

    private String name;
    private String uri;
    private String method;
    private String description;
    private String parentId;
    private String sort;
    private String component;
    private String icon;
    private ResourceTypeEnum type;

    public Resource(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }
}
