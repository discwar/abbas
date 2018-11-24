package com.major.common.util;

import java.util.*;

/**
 * <p>Title: 安全认证工具类 </p>
 * <p>Description: 生成token、签名等信息 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/9 10:26      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class SecurityUtils {

    public static String createToken(){
        return TokenGenerator.getInstance().generateToken();
    }

    /**
     * 获取签名
     * @param requestMap 参与签名的参数
     * @param appSecret 秘钥
     * @return
     */
    public static String getSign(Map<String, String> requestMap, String appSecret) {
        String requestString = getStringByKeySort(requestMap);
        String sign = Md5Utils.stringToMD5(appSecret + requestString + appSecret);
        return sign.toUpperCase();
    }

    /**
     * 1、将参与签名的参数按照键值(key)进行字典排序
     * 2、将排序过后的参数，进行key和value字符串拼接
     * @param requestMap 参与签名的参数
     * @return
     */
    public static String getStringByKeySort(Map<String, String> requestMap) {
        // 按Key值进行字典排序
        Collection<String> keySet = requestMap.keySet();
        List<String> list = new ArrayList<>(keySet);
        Collections.sort(list);

        //拼凑签名字符串
        StringBuffer signStr = new StringBuffer();
        for (int i=0; i<list.size(); i++) {
            String key = list.get(i);
            signStr.append(key + requestMap.get(key));
        }

        return signStr.toString();
    }

    /**
     * https://newopen.imdada.cn/#/development/file/order?_k=7zb7rm
     * 针对达达回调校验签名
     * @param requestMap
     * @return
     */
    public static String getCallbackSign(Map<String, String> requestMap) {
        String requestString = getStringByValueSort(requestMap);
        return Md5Utils.stringToMD5(requestString);
    }

    /**
     * 按Value值进行字典排序
     * @param requestMap
     * @return
     */
    public static String getStringByValueSort(Map<String, String> requestMap) {
        Collection<String> valuesSet = requestMap.values();
        List<String> list = new ArrayList<>(valuesSet);
        Collections.sort(list);

        //拼凑签名字符串
        StringBuffer signStr = new StringBuffer();
        for (String value : list) {
            signStr.append(value);
        }

        return signStr.toString();
    }

    public static void main(String[] args) {
        String token = SecurityUtils.createToken();
        System.out.println(token);

        Map<String, String> requestMap = new HashMap();
        requestMap.put("phone", "18050430355");
        requestMap.put("timestamp", "1531100151");
        requestMap.put("captcha", "1234");
//        requestMap.put("app_version", "1.0");
//        requestMap.put("os_type", "0");
        String appSecret = "agAppSecret";

        String sign = SecurityUtils.getSign(requestMap, appSecret);
        System.out.println(sign);
    }

}
