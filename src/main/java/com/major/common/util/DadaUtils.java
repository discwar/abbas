package com.major.common.util;


import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.config.DadaConfig;
import com.major.model.request.DadaOrderNotifyRequest;
import com.major.model.response.DadaApiResponse;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
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
public class DadaUtils {

    /**
     * 请求格式，暂时只支持json，固定
     */
    private static final String FORMAT_JSON = "json";

    /**
     * 订单详情查询URI
     */
    private static final String URI_ORDER_QUERY_API = "/api/order/status/query";

    /**
     * 新增订单
     */
    public static final String URI_ADD_ORDER_API ="/api/order/addOrder";

    public static final String URI_RE_ADD_ORDER_API ="/api/order/reAddOrder";

    /**
     * 新增门店
     */
    public static String SHOP_ADD_URL = "/api/shop/add";

    /**
     * 成功状态码
     */
    public static final String SUCCESS = "success";

    /**
     * 失败状态码
     */
    public static final String FAIL = "fail";

    public final static String V = "1.0";

    /**
     * 订单不存在,请核查订单号
     */
    public static final String NO_ORDER_CODE="2005";
    /**
     * 取消原因:其他
     */
    public static final Integer OthersReason = 10000;

    /**
     *
     * {"reason":"没有配送员接单","id":1},{"reason":"配送员没来取货","id":2},{"reason":"配送员态度太差","id":3},{"reason":"顾客取消订单","id":4},{"reason":"订单填写错误","id":5},{"reason":"配送员让我取消此单","id":34},{"reason":"配送员不愿上门取货","id":35},{"reason":"我不需要配送了","id":36},{"reason":"配送员以各种理由表示无法完成订单","id":37},{"reason":"其他","id":10000}
     * 客户取消订单原因Id
     */
    public static final Integer USER_CANCEL_ORDER = 4;

    /**
     * POST请求发送
     * @param url
     * @param body
     * @param dadaConfig
     * @return
     */
    private static JSONObject sendPost(String url, String body, DadaConfig dadaConfig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject param = new JSONObject();
        param.put("app_key", dadaConfig.getAppKey());
        // 时间戳，单位秒，即unix-timestamp
        param.put("timestamp", DateUtils.getTimestamp());
        param.put("format", FORMAT_JSON);
        param.put("v", dadaConfig.getV());
        param.put("source_id", dadaConfig.getSourceId());
        param.put("body", body);

        // 获取签名
        Map<String, String> parameterMap = JSONObject.parseObject(param.toJSONString(), new TypeReference<Map<String, String>>(){});
        String sign = SecurityUtils.getSign(parameterMap, dadaConfig.getAppSecret());
        param.put("signature", sign);

        HttpEntity<JSONObject> requestBody = new HttpEntity<>(param, headers);
        JSONObject result = HttpUtils.postResponseResult(url, requestBody);

        return result;
    }

    /**
     * 订单详情查询
     * 参考：http://newopen.imdada.cn/#/development/file/statusQuery?_k=b9mjqo
     * @param orderNo
     * @param dadaConfig
     * @return
     */
    public static JSONObject queryOrderDetail(String orderNo, DadaConfig dadaConfig) {
        String url = dadaConfig.getHost() + URI_ORDER_QUERY_API;

        JSONObject requestBody = new JSONObject();
        requestBody.put("order_id", String.valueOf(orderNo));
        String body = requestBody.toJSONString();

        return DadaUtils.sendPost(url, body, dadaConfig);
    }

    public static JSONObject getCityCode(DadaConfig dadaConfig) {
        String url = dadaConfig.getHost() + "/api/cityCode/list";

        return DadaUtils.sendPost(url, "", dadaConfig);
    }

    public  static  JSONObject getCancelReasons(DadaConfig dadaConfig){
        String url = dadaConfig.getHost() + "/api/order/cancel/reasons";

        return DadaUtils.sendPost(url, "", dadaConfig);
    }

    /**
     *达达新增门店、新增订单
     * @param params 门店相关信息
     * @param dadaConfig 达达配置
     * @return
     */
    public static DadaApiResponse callRpc(String params,DadaConfig dadaConfig,String path) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("source_id", dadaConfig.getSourceId());
        requestParams.put("app_key", dadaConfig.getAppKey());
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        requestParams.put("format", FORMAT_JSON);
        requestParams.put("v", V);
        requestParams.put("body", params);
        // 获取签名
        String sign = SecurityUtils.getSign(requestParams, dadaConfig.getAppSecret());
        requestParams.put("signature", sign);
        String url = dadaConfig.getHost() + path;
        String requestParam=JSONUtil.toJson(requestParams);
        try {
            String resp = HttpUtils.postRequest(url, requestParam);
            return JSONUtil.fromJson(resp, DadaApiResponse.class);
        }catch (Exception e){
            return DadaApiResponse.except();
        }
    }


    /**
     * 取消订单
     * 返回字段 deduct_fee 扣除的违约金(单位：元)
     * @param dadaConfig
     * @param orderNo
     * @param cancelReasonId
     * @param cancelReason
     * @return
     */
    public static Double cancelOrder(DadaConfig dadaConfig, String orderNo, Integer cancelReasonId, String cancelReason){
        //当取消原因ID为其他时，此字段必填(10000为其他)
        if (cancelReasonId.equals(OthersReason) && org.apache.commons.lang3.StringUtils.isEmpty(cancelReason)){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"取消原因不能为空");
        }
        JSONObject param = new JSONObject();
        param.put("order_id",orderNo);
        param.put("cancel_reason_id",cancelReasonId);
        if (StringUtils.isNotEmpty(cancelReason)){
            param.put("cancel_reason",cancelReason);
        }
        String requestString = JSONObject.toJSONString(param);
        String url = dadaConfig.getHost()+ "/api/order/formalCancel";
        JSONObject jsonObject = sendPost(url, requestString, dadaConfig);
        String status = jsonObject.getString("status");
        if (StringUtils.equals(DadaUtils.FAIL, status)) {
            String msg = jsonObject.getString("msg");
            throw new AgException(StatusResultEnum.DADA_ORDER_CANCEL_FAIL, msg);
        }
        Map<String,Object> result = (Map<String, Object>) jsonObject.get("result");
        return   (Double) result.get("deduct_fee");
    }
    /**
     * 检查CallBack签名
     * @param request
     * @return
     */
    public static String checkCallBackSign(DadaOrderNotifyRequest request){
        Map<String,String> requestMap = new HashMap<>(3);
        requestMap.put("client_id",request.getClientId());
        requestMap.put("order_id",request.getOrderId());
        requestMap.put("update_time",request.getUpdateTime().toString());
        return SecurityUtils.getCallbackSign(requestMap);
    }

    public static void main(String[] args) {
        Map<String,String> requestMap = new HashMap<>(3);
        requestMap.put("client_id","277940415246393");
        requestMap.put("order_id","32175844764028928");
        requestMap.put("update_time","1540784328");
        System.out.println(""+SecurityUtils.getCallbackSign(requestMap));

    }
}
