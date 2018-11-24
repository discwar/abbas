package com.major.job;

import com.major.common.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 执行定时任务
 * @Author: zhangzhenliang
 * @Date: 2018/11/12 17:14
 * @Version 1.0
 */
public class ScheduleCallable implements Callable {
    private Object target;
    private Method method;
    private String params;

    public ScheduleCallable(String beanName, String methodName, String params)
            throws NoSuchMethodException, SecurityException {
        this.target = SpringUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotEmpty(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public String call(){
        String result="";
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params)) {
                result=(String) method.invoke(target, params);
            } else {
                result=(String) method.invoke(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
