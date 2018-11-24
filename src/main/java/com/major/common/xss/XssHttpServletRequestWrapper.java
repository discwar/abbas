package com.major.common.xss;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS过滤处理
 * 
 * @author LianGuoQing
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if(StringUtils.isNotEmpty(value)){
            value = Jsoup.clean(value, Whitelist.relaxed()).trim();
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);

        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];

            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapeValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            }

            return escapeValues;
        }

        return super.getParameterValues(name);
    }

}