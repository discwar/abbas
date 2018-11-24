package com.major.common.aspectj;

import com.major.common.aspectj.annotation.Log;
import com.major.common.constant.UserConstants;
import com.major.common.util.AddressUtils;
import com.major.common.util.ServletUtils;
import com.major.common.util.ShiroUtils;
import com.major.entity.SysOperLog;
import com.major.entity.SysUser;
import com.major.service.ISysOperLogService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 * 
 * @author LianGuoQing
 */
@Aspect
@Component
@EnableAsync
@Slf4j
public class LogAspect {

    @Autowired
    private ISysOperLogService sysOperLogService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(Log)")
    public void logPointCut() {
    }

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        this.handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        this.handleLog(joinPoint, e);
    }

    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            SysUser currentUser = ShiroUtils.getSysUser();

            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setStatus(UserConstants.NORMAL);

            // 请求的地址
            String ip = ShiroUtils.getIp();
            sysOperLog.setOperIp(ip);

            // 操作地点
            sysOperLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());

            if (currentUser != null) {
                sysOperLog.setLoginName(currentUser.getUsername());
            }

            if (e != null) {
                sysOperLog.setStatus(UserConstants.EXCEPTION);
                sysOperLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysOperLog.setMethod(className + "." + methodName + "()");

            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, sysOperLog);

            // 保存数据库
            sysOperLogService.insertSysOperLog(sysOperLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log
     * @param sysOperLog
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SysOperLog sysOperLog) throws Exception {
        // 设置action动作
        sysOperLog.setAction(log.action());
        // 设置标题
        sysOperLog.setTitle(log.title());
        // 设置channel
        sysOperLog.setChannel(log.channel());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            this.setRequestValue(sysOperLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param sysOperLog
     */
    private void setRequestValue(SysOperLog sysOperLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        sysOperLog.setOperParam(StringUtils.substring(params, 0, 255));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }

        return null;
    }

}
