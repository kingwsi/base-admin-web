package com.kingwsi.bs.entity.role;

import com.kingwsi.bs.entity.common.BaseEntityVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends BaseEntityVO {
    private String name;
    private String status;
    private String description;
    private List<String> resourceIds;
}
