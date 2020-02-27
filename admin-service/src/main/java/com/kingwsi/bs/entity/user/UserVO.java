package com.kingwsi.bs.entity.user;

import com.kingwsi.bs.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description: []
 * Name: UserVO
 * Author: wangshu
 * Date: 2019/6/29 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String username;
    private String password;
    private Boolean remember;
    private String fullName;
    private String avatar;
    private String introduction;
    private List<String> roles;
}
