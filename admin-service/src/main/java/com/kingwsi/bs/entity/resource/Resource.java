package com.kingwsi.bs.entity.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Description: 权限实体<br>
 * Comments Name: Resource<br>
 * Date: 2019/7/11 15:45<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resources")
public class Resource implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
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
