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
}
