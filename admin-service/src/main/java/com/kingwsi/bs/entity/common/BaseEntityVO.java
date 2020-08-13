package com.kingwsi.bs.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

/**
 * description: BaseEntityVO <br>
 * date: 2020/8/7 9:50 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Data
public class BaseEntityVO {
    private String id;
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant createdDate;
    private String lastUpdater;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant lastUpdateDate;
    private Boolean deleted;
}
