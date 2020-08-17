package com.kingwsi.bs.common.handler;

import com.kingwsi.bs.entity.common.BaseEntity;
import com.kingwsi.bs.common.security.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Properties;

/**
 * Description: sql拦截器，拦截新增/更新操作，对sql进行，记录操作者/操作时间
 * Author: wangshu
 * Date: 2020/3/30 22:34
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
@Slf4j
public class AuditorHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (parameter instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) parameter;
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                entity.setCreatedDate(Instant.now());
                entity.setCreator(TokenUtil.getUserId());
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                entity.setLastUpdater(TokenUtil.getUserId());
                entity.setLastUpdateDate(Instant.now());
            }

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
