package top.dongxibao.erp.annotation;

import top.dongxibao.erp.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义接口日志记录注解
 *
 * @author Dongxibao
 * @date 2020-11-27
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块 
     */
    String title() default "";
    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;
    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
