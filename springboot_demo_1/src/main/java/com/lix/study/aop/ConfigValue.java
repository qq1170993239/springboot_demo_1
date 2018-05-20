package com.lix.study.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从配置服务表（lix_base_config）上获取配置的值
 * @author lix
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigValue {

    /**
     * 设为lix_base_config.code的值,code与type为联合主键
     * @return
     */
    String code() default "";
    
    /**
     * 设为lix_base_config.type的值,type可以相同
     * @return
     */
    String type() default "";

    /**
     * 是否必须
     * @return
     */
    boolean required() default true;
}
