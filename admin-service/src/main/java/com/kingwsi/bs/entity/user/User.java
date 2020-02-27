package com.kingwsi.bs.entity.user;

import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Description: 用户<br>
 * Comments Name: User<br>
 * Date: 2019/6/28 18:20<br>
 * Author: Administrator<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    private String avatar;

    private String introduction;

    private String fullName;
}
