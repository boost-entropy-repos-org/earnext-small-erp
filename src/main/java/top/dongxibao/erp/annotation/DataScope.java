package top.dongxibao.erp.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 部门表的别名
     */
    String deptAlias() default "d";

    /**
     * username表的别名
     */
    String userAlias() default "a";
}
