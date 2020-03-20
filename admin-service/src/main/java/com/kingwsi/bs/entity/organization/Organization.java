package com.kingwsi.bs.entity.organization;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kingwsi.bs.entity.common.BaseEntity;
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
@TableName("sys_organizations")
public class Organization extends BaseEntity {
    private String name;
    private String description;
    private String parentId;
    private String remark;
}
