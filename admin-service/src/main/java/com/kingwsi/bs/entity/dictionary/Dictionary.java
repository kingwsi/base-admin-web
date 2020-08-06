package com.kingwsi.bs.entity.dictionary;

import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.Data;

/**
 * description: Dictionary <br>
 * date: 2020/8/6 10:29 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Data
public class Dictionary extends BaseEntity {
    private String code;
    private String name;
    private String description;
    private String sort;
}
