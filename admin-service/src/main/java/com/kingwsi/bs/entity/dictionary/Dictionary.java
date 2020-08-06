package com.kingwsi.bs.entity.dictionary;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: Dictionary <br>
 * date: 2020/8/6 10:29 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dictionaries")
public class Dictionary extends BaseEntity {
    private String code;
    private String name;
    private String description;
    private String sort;
}
