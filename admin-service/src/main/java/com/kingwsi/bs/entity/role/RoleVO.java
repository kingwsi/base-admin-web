package com.kingwsi.bs.entity.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {
    private String id;
    private String name;
    private String description;
    private List<String> resourceIds;
}
