package com.major.common.util;

import com.major.config.KuaiDi100Config;
import com.major.model.merchant.LastResult;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

;

/**
 * <p>Title: 快递100工具类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/6 10:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class KuaiDi100Utils {

    /**
     * 快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
     */
    public static final String STATE_FINISHED = "3";

    public static final String RESULT_FALSE = "false";

    public static final String RESULT_STATUS="200";


    /**
     * 快递100实时快递查询接口
     * 参考：https://www.kuaidi100.com/openapi/api_post.shtml
     * @param config
     * @return
     */
    public static String query(KuaiDi100Config config) {
        String param = config.getParam();
        String customer = config.getCustomer();
        // 签名，用于验证身份，按param + key + customer的顺序进行MD5加密（注意加密后字符串一定要转大写）
        String plainText = param + config.getKey() + customer;
        String sign = Md5Utils.stringToMD5(plainText).toUpperCase();

        StringBuilder query = new StringBuilder("?");
        query.append("customer=").append(customer)
                .append("&sign=").append(sign)
                .append("&param={param}");

        String url = config.getQueryHost() + query.toString();
        return HttpUtils.postResponseResult(url, null, String.class, param);
    }

    /**
     * 封装查询参数
     * @param com 查询的快递公司的编码，一律用小写字母，必须
     * @param num 查询的快递单号，单号的最大长度是32个字符，必须
     * @return
     */
    public static String getQueryParam(String com, String num) {
        JSONObject paramJson = new JSONObject();
        paramJson.put("com", com);
        paramJson.put("num", num);
        return paramJson.toJSONString();
    }

    /**
     * 封装订阅请求体
     * @param com 订阅的快递公司的编码，一律用小写字母
     * @param num 订阅的快递单号，单号的最大长度是32个字符
     * @param callbackUrl 回调接口的地址
     * @param key 授权码
     * @return
     */
    public static String getSendQueryParam(String com, String num,String callbackUrl,String key){
        JSONObject paramJson = new JSONObject();
        paramJson.put("company",com);
        paramJson.put("number",num);
        paramJson.put("key",key);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("callbackurl",callbackUrl);
        paramJson.put("parameters",map);
        return paramJson.toJSONString();
    }

    /**
     * 发送订单请求
     * 参考 https://poll.kuaidi100.com/pollquery/pollTechWord.jsp
     * @param config
     * @return
     */
    public static String sendSubscribe(KuaiDi100Config config){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("schema","json");
        map.put("param",config.getParam());
        String url = config.getHost() ;
        return HttpUtils.sendPost(url,map);
    }

    public static void main(String[] args) {
        KuaiDi100Config kuaiDi100Config = new KuaiDi100Config();
        kuaiDi100Config.setCustomer("C368E90B942D1776AD9F7516C5AFEA26");
        kuaiDi100Config.setKey("EDvARSZS2735");
        kuaiDi100Config.setQueryHost("http://poll.kuaidi100.com/poll/query.do");
        kuaiDi100Config.setParam(KuaiDi100Utils.getQueryParam("zhongtong","75104338023365"));
        String resp = KuaiDi100Utils.query(kuaiDi100Config);
        System.out.println(resp);
        JSONObject resultJSONObject = JSONObject.parseObject(resp);
        String result = resultJSONObject.getString("status");
        if (!KuaiDi100Utils.RESULT_STATUS.equals(result)) {
           return;
        }
        LastResult lastResult= JSONUtil.fromJson(resp, LastResult.class);
        System.out.println(lastResult.getState());

    }

}
