package com.major.common.util;

import com.major.common.constant.Constants;
import com.major.entity.SysLoginLog;
import com.major.service.ISysLoginLogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * 记录用户日志信息
 *
 * @author LianGuoQing
 */
@Slf4j
public class SystemLogUtils {

    /**
     * 记录格式 [ip][用户名][操作][错误消息]
     * <p/>
     * 注意操作如下： loginError 登录失败 loginSuccess 登录成功 passwordError 密码错误 changePassword 修改密码 changeStatus 修改状态
     *
     * @param username
     * @param status
     * @param msg
     * @param args
     */
    public static void log(String username, String status, String msg, Object... args) {
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
            saveOpLog(username, msg, Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            saveOpLog(username, msg, Constants.FAIL);
        }
    }

    public static void saveOpLog(String username, String message, String status) {
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));

            // 获取客户端操作系统
            String os = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();

            SysLoginLog sysLoginLog = new SysLoginLog();
            sysLoginLog.setLoginName(username);
            sysLoginLog.setStatus(status);
            sysLoginLog.setIpAddress(ShiroUtils.getIp());
            sysLoginLog.setLoginLocation(AddressUtils.getRealAddressByIP(ShiroUtils.getIp()));
            sysLoginLog.setBrowser(browser);
            sysLoginLog.setOs(os);
            sysLoginLog.setMsg(message);
            sysLoginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));

            ISysLoginLogService sysLogServiceImpl = SpringUtils.getBean("sysLoginLogServiceImpl");
            sysLogServiceImpl.insertSysLoginLog(sysLoginLog);
        }catch (Exception e){
            log.error("==前置通知异常==");
            log.error("异常信息:{}", e.getMessage());
            e.printStackTrace();
        }

    }

}
