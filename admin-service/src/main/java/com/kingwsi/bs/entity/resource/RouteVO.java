package com.kingwsi.bs.entity.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteVO {

    @NotBlank
    private String uri;

    @NotBlank
    private String parentId;
    private String sort = "-1";

    private String icon = "icon";
}
