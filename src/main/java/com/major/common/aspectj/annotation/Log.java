package com.major.common.aspectj.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author LianGuoQing
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     * @return
     */
    String title() default "";

    /**
     * 功能
     * @return
     */
    String action() default "";

    /**
     * 渠道
     * @return
     */
    String channel() default "web";

    /**
     * 是否保存请求的参数
     * @return
     */
    boolean isSaveRequestData() default true;

}
