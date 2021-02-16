package top.dongxibao.erp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *@ClassName CreateBy
 *@Description 1
 *@Author Dongxibao
 *@Date 2021/1/11
 *@Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface CreateBy {
}

