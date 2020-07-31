package com.kingwsi.bs.common.helper.tableInfo;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.kingwsi.bs.common.enumerate.FilterRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TablePermissionHelper {

    private static final Map<String, TablePermissionInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    public static TablePermissionInfo getTablePermissionInfo(String tableName) {
        TablePermissionInfo tablePermissionInfo = TABLE_INFO_CACHE.get(tableName);
        if (tablePermissionInfo == null) {
            return new TablePermissionInfo();
        }
        return TABLE_INFO_CACHE.get(tableName);
    }

    public static void initTablePermissionInfo() {
        List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        for (TableInfo tableInfo: tableInfos) {
            TablePermissionInfo tablePermissionInfo = new TablePermissionInfo();
            String tableName = tableInfo.getTableName();

            List<TableFieldInfo> fieldList = tableInfo.getFieldList();

            for (TableFieldInfo tableFieldInfo : fieldList) {
                String column = tableFieldInfo.getColumn();
                if (FilterRule.ORGANIZATION.getColumn().equals(column)) {
                    tablePermissionInfo.setHasOrganization(true);
                }
                if (FilterRule.CREATOR.getColumn().equals(column)) {
                    tablePermissionInfo.setHasCreator(true);
                }
                TABLE_INFO_CACHE.put(tableName, tablePermissionInfo);
            }
        }
        log.info("表权限信息初始化完成");
    }
}
