package com.kingwsi.bs.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Description: 实体超类<br>
 * Comments Name: BaseEntity<br>
 * Date: 2019/7/12 18:08<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    String id;

    @CreatedBy
    String creator;

    @CreatedDate
    Instant createdDate;

    @LastModifiedBy
    String lastUpdater;

    @LastModifiedDate
    Instant lastUpdateDate;

    @TableLogic
    Boolean deleted;

    /**
     * 数据权限控制,组织id，每条数据只可拥有一个组织
     * 默认0无效
     */
    private String organizationId = "0";

    /**
     * 数据权限控制
     * 可拥有多个角色，满足其中任意一个角色即可
     * 默认值0，不生效
     */
    private String roleIds = "0";
}
