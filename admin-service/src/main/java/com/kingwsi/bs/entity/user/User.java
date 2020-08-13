package com.kingwsi.bs.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * Description: 用户<br>
 * Comments Name: User<br>
 * Date: 2019/6/28 18:20<br>
 * Author: Administrator<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_users")
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "fullName")
    private String fullName;

    // 状态 0 禁用 1 启用
    @Column(name = "status")
    private String status;
}
