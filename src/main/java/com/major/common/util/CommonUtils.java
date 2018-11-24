package com.major.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: XuQuanMing
 * @Date: 2018/7/13 9:46
 * @Description: 数字相关工具包
 */
public class CommonUtils {

    private static final String UN_KNOWN = "unKnown";

    /**
     *手机号码验证
     * @param phone 手机号
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        return regexMatches(regex, phone);
    }

    /**
     * 验证码生成
     * @return
     */
    public static String getRandom(int figures) {
        String num = "";
        for (int i = 0; i < figures; i++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }

    /**
     * 正则匹配字符串
     * @param regex 正则表达式
     * @param input 字符串
     * @return 匹配到返回true
     */
    public static boolean regexMatches(String regex, String input) {
        Pattern pattern  = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }

        return false;
    }

    /**
     * 字符串数据处理
     */
    public static String valueAsStr(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    /**
     * 整型数据处理
     */
    public static Integer valueAsInt(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            if ("NaN".equals(value)) {
                return null;
            }
            return Integer.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1 : 0;
        } else {
            return null;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 将对象转成Map
     * @param object
     * @return
     */
    public static Map object2Map(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        return JSON.parseObject(jsonObject.toJSONString(), Map.class);
    }

    /**
     * 将Map转换为对象
     * @param paramMap
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1) {
                return ip.substring(0,index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getRemoteAddr();

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 生成流水号（18位）
     * 格式：日期（8位）+支付方式（1位）+账单类型（1位）+交易类型（1位）+订单号（7位，若为空，则随机产生）
     * @param payWay
     * @param billType
     * @param transactionType
     * @param orderNo
     * @return
     */
    public static String generateFlowNo(Integer payWay, Integer billType, Integer transactionType, String orderNo) {
        String date = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.getNowDate());

        if (StringUtils.isNotBlank(orderNo)) {
            orderNo = StringUtils.substring(orderNo, 0, 7);
        } else {
            orderNo = getRandom(7);
        }

        return date + payWay + billType + transactionType + orderNo;
    }

}
