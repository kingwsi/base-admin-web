package com.kingwsi.bs.common.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.kingwsi.bs.common.enumerate.FilterRule;
import com.kingwsi.bs.common.helper.tableInfo.TablePermissionHelper;
import com.kingwsi.bs.common.helper.tableInfo.TablePermissionInfo;
import com.kingwsi.bs.entity.authority.Principal;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Optional;

//@Slf4j
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
//public class MyPaginationInterceptor extends PaginationInterceptor {
//
//    /**
//     * 查询总记录条数
//     *
//     * @param sql             count sql
//     * @param mappedStatement MappedStatement
//     * @param boundSql        BoundSql
//     * @param page            IPage
//     * @param connection      Connection
//     */
//    @Override
//    protected void queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql, IPage<?> page, Connection connection) {
//        System.out.println("查询总记录条数");
//        super.queryTotal(sql, mappedStatement, boundSql, page, connection);
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || "anonymousUser".equals(authentication.getPrincipal())) {
//            return super.intercept(invocation);
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof Principal) {
//            Principal principal1 = (Principal) principal;
//            FilterRule filterRule = principal1.getFilterRule();
//            if (!StringUtils.isEmpty(filterRule)) {
//                log.info("过滤规则--->{}", filterRule);
//                StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
//                MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//                BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
//
//                // 使用CCJSql解析sql
//                Select selectStatement = (Select) CCJSqlParserUtil.parse(boundSql.getSql());
//                PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
//                FromItem fromItem = plainSelect.getFromItem();
//                // 获取表名
//                String tableName = Optional.ofNullable(fromItem.getAlias()).map(Alias::getName).orElse(fromItem.toString());
//
//                // 获取当前表权限信息
//                TablePermissionInfo tablePermissionInfo = TablePermissionHelper.getTablePermissionInfo(plainSelect.getFromItem().toString());
//                String whereStr = Optional.ofNullable(plainSelect.getWhere()).map(where -> where + " AND ").orElse(" WHERE");
//                // 创建者过滤
//                if (filterRule == FilterRule.CREATOR && tablePermissionInfo.isHasCreator()) {
//                    whereStr = whereStr + tableName + "." + filterRule + " = '" + principal1.getUser().getId()+"'";
//                }
//                // 组织过滤
//                if (filterRule == FilterRule.ORGANIZATION && tablePermissionInfo.isHasOrganization()) {
//                    whereStr = whereStr + tableName + "." + filterRule + " IN " + principal1.getUser().getId();
//                }
//
//                Expression expression = CCJSqlParserUtil.parseCondExpression(whereStr);
//                plainSelect.setWhere(expression);
//                String edited = plainSelect.toString();
//                log.info("修改后sql -> {}", edited);
//                Field field = boundSql.getClass().getDeclaredField("sql");
//                field.setAccessible(true);
//                field.set(boundSql, edited);
//            }
//        }
//        return super.intercept(invocation);
//    }
//}
