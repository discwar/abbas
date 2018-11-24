package com.major.shiro.filter;

import com.major.common.constant.ShiroConstants;
import com.major.common.util.ShiroUtils;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 */
public class CaptchaValidateFilter extends AccessControlFilter {

    /**
     * 是否开启验证码
     */
    private boolean captchaEbabled = true;

    /**
     * 验证码类型
     */
    private String captchaType = "math";

    public void setCaptchaEbabled(boolean captchaEbabled) {
        this.captchaEbabled = captchaEbabled;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_EBABLED, captchaEbabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 验证码禁用 或不是表单提交 允许访问
        if (captchaEbabled == false || !StringUtils.equals(RequestMethod.POST.name(), httpServletRequest.getMethod())) {
            return true;
        }

        return validateResponse(httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATE_CODE));
    }

    public boolean validateResponse(String validateCode) {
        Object obj = ShiroUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");

        if (StringUtils.isEmpty(validateCode)
                || !validateCode.equalsIgnoreCase(code)) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }
}
