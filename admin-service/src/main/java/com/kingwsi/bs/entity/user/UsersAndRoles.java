package com.kingwsi.bs.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户角色关实体<br>
 * Comments Name: UsersAndRoles<br>
 * Date: 2019/7/11 17:38<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_users_and_roles")
public class UsersAndRoles {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String roleId;
}
