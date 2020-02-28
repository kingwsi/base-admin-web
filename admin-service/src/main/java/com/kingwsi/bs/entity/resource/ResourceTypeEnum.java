package com.kingwsi.bs.entity.resource;

public enum ResourceTypeEnum {
    ROUTE("路由"),
    BUTTON("按钮"),
    URL("请求地址");

    private String description;

    ResourceTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
