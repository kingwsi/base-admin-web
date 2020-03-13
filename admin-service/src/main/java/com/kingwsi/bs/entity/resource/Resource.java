package com.kingwsi.bs.entity.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("resources")
public class Resource implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private ResourceTypeEnum type;
    private String uri;
    private String method;
    private String parentId;
    private Integer sort;

    public Resource(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }
}
