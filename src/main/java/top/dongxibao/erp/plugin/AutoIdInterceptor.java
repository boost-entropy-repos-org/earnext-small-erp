package top.dongxibao.erp.plugin;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import top.dongxibao.erp.annotation.*;
import top.dongxibao.erp.security.SecurityUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;


/**
 * @author Dongxibao
 * @Description: mybatis ID自增拦截器
 * @date 2021-1-4
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class
        }),
})
@Slf4j
public class AutoIdInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // sql 类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        // 获取父类私有成员变量
        Field[] declaredFields = parameter.getClass().getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                if (field.getAnnotation(AutoId.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, IdUtil.getSnowflake(1, 1).nextId());
                }
                if (field.getAnnotation(CreateTime.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
                if (field.getAnnotation(CreateBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, SecurityUtils.getCurrentUsername());
                }
            }
            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (field.getAnnotation(UpdateTime.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
                if (field.getAnnotation(UpdateBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, SecurityUtils.getCurrentUsername());
                }
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
