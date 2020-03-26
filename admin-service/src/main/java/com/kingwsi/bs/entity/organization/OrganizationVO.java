package com.kingwsi.bs.entity.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 角色<br>
 * Comments Name: Role<br>
 * Date: 2019/7/11 16:29<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationVO {
    private String id;
    private String name;
    private String description;
    private String parentId;
    private String remark;
}
