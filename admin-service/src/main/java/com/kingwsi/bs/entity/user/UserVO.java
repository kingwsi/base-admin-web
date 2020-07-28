package com.kingwsi.bs.entity.user;

import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
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
    private String id;

    @Length(min = 5, max = 15,message = "用户名长度需在5-15之间")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Length(min = 5, max = 20,message = "密码长度需在5-20之间")
    @NotBlank(message = "密码不能为空")
    private String password;
    private Boolean remember;
    private String fullName;
    private String avatar;
    private String introduction;
    private List<String> roles;
    private String creator;
    private Instant createdDate;
    private String lastUpdater;
    private Instant lastUpdateDate;
    private String organizationId;
    private String roleIds;

}
