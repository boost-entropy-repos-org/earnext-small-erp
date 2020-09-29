package top.dongxibao.erp.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解防止表单重复提交
 *
 * @author Dongxibao
 * @date 2020-06-06
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /** 表单重复校验默认5秒 */
    int expiredTime() default 5;
}