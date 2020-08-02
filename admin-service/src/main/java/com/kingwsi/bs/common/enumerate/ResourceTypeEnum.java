package com.kingwsi.bs.common.enumerate;

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

    public String getName(){
        return this.name();
    }
}
