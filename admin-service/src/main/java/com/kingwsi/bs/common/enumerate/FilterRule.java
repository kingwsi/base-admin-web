package com.kingwsi.bs.common.enumerate;

public enum FilterRule {
    ORGANIZATION("组织", "organization_id"), ROLE("角色", "role_id"), CREATOR("创建者", "creator");

    private String name;
    private String column;

    FilterRule(String name, String column) {
        this.name = name;
        this.column = column;
    }

    public String getName() {
        return this.name;
    }

    public String getColumn() {
        return this.column;
    }
}
