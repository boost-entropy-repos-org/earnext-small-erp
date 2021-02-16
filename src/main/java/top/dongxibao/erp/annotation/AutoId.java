package top.dongxibao.erp.annotation;

import java.lang.annotation.*;

/**
 * 主键注解
 *
 * @author Dongxibao
 * @date 2021-1-4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoId {

}
