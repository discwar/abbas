package com.major.shiro.filter;

import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.util.MessageUtils;
import com.major.common.util.ShiroUtils;
import com.major.common.util.SystemLogUtils;
import com.major.entity.SysUser;
import com.major.model.response.ResultResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 退出过滤器
 *
 * @author LianGuoQing
 */
@Slf4j
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    
    /**
     * 退出后重定向的地址
     */
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response)
            throws Exception {
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = this.getRedirectUrl(request, response, subject);

            try {
                SysUser user = (SysUser) ShiroUtils.getSubject().getPrincipal();
                if (user != null) {
                    String loginName = user.getUsername();
                    // 记录用户退出日志
                    SystemLogUtils.log(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success"));
                }

                // 退出登录
                subject.logout();
            } catch (SessionException ise) {
                log.error("logout fail.", ise);
            }

            PrintWriter out = response.getWriter();
            out.println(JSONObject.toJSONString(ResultResponse.success(StatusResultEnum.SUCCESS)));
            out.flush();
            out.close();

//            issueRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }

        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url)) {
            return url;
        }

        return super.getRedirectUrl(request, response, subject);
    }

}
