package com.kingwsi.bs.common.enumerate;

public enum ResourceTypeEnum {
    MENU("路由"),
    BUTTON("按钮"),
    API("请求地址");

    private String description;

    ResourceTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
