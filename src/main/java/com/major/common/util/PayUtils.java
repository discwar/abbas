package com.major.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.XML;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.util.*;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/25 10:09      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class PayUtils {

    /**
     * 微信交易类型：app支付
     */
    public static final String TRADE_TYPE = "APP";

    /**
     * 微信统一下单API接口，固定值
     */
    public static final String WX_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 成功状态码
     */
    public static final String WX_SUCCESS = "SUCCESS";

    /**
     * 失败状态码
     */
    public static final String WX_FAIL = "FAIL";

    public static final String HMAC_SHA256 = "HMAC-SHA256";

    public static final String SIGN_TYPE = "RSA2";
    public static final String CHARSET = "UTF-8";
    public static final String FORMAT = "json";


    /**
     * 将Map参数集合转换成xml结构
     * @param param Map参数集合
     * @return
     */
    public static String getRequestXML(SortedMap<Object, Object> param) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        Set set = param.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key =String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            // 过滤相关字段sign
            if("sign".equalsIgnoreCase(key)){
                buffer.append("<"+key+">"+"<![CDATA["+value+"]]>"+"</"+key+">");
            }else{
                buffer.append("<"+key+">"+value+"</"+key+">");
            }
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    /**
     * 将xml字符串转换成org.json.JSONObject格式
     * @param xml
     * @return
     */
    public static org.json.JSONObject convertXmlToJson(String xml) {
        org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml.replace("<xml>", "").replace("</xml>", ""));
        return xmlJSONObj;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static SortedMap<Object, Object> xmlToMap(String strXML) throws ParserConfigurationException, IOException, SAXException {
        SortedMap<Object, Object> data = new TreeMap<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }

        stream.close();

        return data;
    }


    /**
     * 微信签名算法
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
     * @param param
     * @param apiKey
     * @return
     */
    public static String createWeiXinSign(SortedMap<Object, Object> param, String apiKey) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Set set = param.entrySet();
        Iterator<?> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = String.valueOf(entry.getKey());
            Object value = String.valueOf(entry.getValue());
            if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                buffer.append(key + "=" + value + "&");
            }
        }

        buffer.append("key=" + apiKey);

        String signType = (String) param.get("sign_type");
        if (StringUtils.equals(HMAC_SHA256, signType)) {
            return PayUtils.hmacSha256(buffer.toString(), apiKey);
        }

        // 默认MD5签名类型
        return Md5Utils.stringToMD5(buffer.toString()).toUpperCase();
    }

    /**
     * 生成HMACSHA256签名
     * @param data 待处理数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String hmacSha256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 微信签名验证
     * @param parameters
     * @param apiKey
     * @return
     */
    public static boolean isSignatureValid(SortedMap<Object, Object> parameters, String apiKey) throws Exception {
        String mySign = PayUtils.createWeiXinSign(parameters, apiKey);
        String wxSign = (String) parameters.get("sign");
        return StringUtils.equals(mySign, wxSign);
    }

    /**
     * 商户处理后同步返回给微信参数
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     * @param returnCode 返回状态码 SUCCESS/FAIL
     * @param returnMsg 返回信息 返回信息，如非空，为错误原因（签名失败、参数格式校验错误）
     * @return
     */
    public static String setXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg
                + "]]></return_msg></xml>";
    }

    /**
     * 将元为单位的金额转换为分，只对小数点前2位支持
     * @param amount 元为单位的金额，比如100.11
     * @return
     */
    public static String yuan2Fen(BigDecimal amount) {
        BigDecimal fenBd = amount.multiply(new BigDecimal(100));
        fenBd = fenBd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return fenBd.toString();
    }

    /**
     * 接收微信的异步通知
     * @param request
     * @return
     * @throws IOException
     */
    public static String receiveWx(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            //建立连接
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            // log.error("连接超时：{}", ce);
        } catch (Exception e) {
            // log.error("https请求异常：{}", e);
        }

        return null;
    }

    /**
     * 获取支付宝POST过来反馈信息（异步通知参数）
     * @param requestParams
     * @return
     */
    public static Map<String, String> getAliRequestParams(Map requestParams) {
        Map<String, String> params = new HashMap<>();
        for (Iterator it = requestParams.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        return params;
    }

    public static void main(String[] args) throws Exception{

        String APP_ID="2016091700528568";
        String APP_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLpRHdDLRZQuwUkBB1tVU3Gd2IRrqYd/0fYDb/fuG3xOswC8/SjcAbU0WLu0Rd1hUrzVEalcw3uyiwMsgj8Tv1HkELhEAedT3BqU96l8do/WoNR2l+H0mx/2rv41Ec7dZd84+zULIasNX8ayvyX7OJBusWsLxRpOekGteHbGcziOPQ3SsfmAGsJFzymP2/vDuFWwMODYkL4X3ZG7leDyURYIOyjE8xx7SLxlRXhDoPAW5O9VahZCp19xeRj+kC5JjTBnBpUbWMsE+STlXyIuX/RCH9jdDOd9qIVRasMhJzuBjG/S6TsHAqcgBIxe+G9ntgmQWPMmpPLD79e//VsobJAgMBAAECggEAWJJ6ohpMydEIrIIz7nT1d92QBb/W2p5AjoYYXDW+NTFRe0gCqSn/j1Vh5x8AXgRLqVyw9IvO3Ap55EhRaXq47OAjoYpgoPD8GrmpD7j1YkJZ4dJJtxVhSOUYjs8/w3mXXj/+BV5JmWRQVaokVbcdyOv6cIno6GAQDKZ5zguT6bsZnUNnFNHskf6iW1pAnT56nyAsMeqOmj/n+ZYoK6PoXBjO3y1tqVg5u6hgAWcMlUeSCnKceA0TxgxWIBNgBWlEnJ2GAlQIqQx9mjRfygRgryDo3EaD3zZvFTpLvD199a3Ez0+9GD3AbJ9hSA+ahEGw2pUul/qRUJHFXYe2t6/xgQKBgQDGzsXKEYYAWsgzv/ZO79fGRhDSvlm4NSTdEbEKcJKMY2rDIxa2Luf8IP6PRZJES6UQ7RAIwY1hMcx1C+EkFvq4DvbcjS80RySHUHjIFoz13yuvBZSMaIgIZ5UW6CR0sBCmz4lPtD8ZKAZvQ/VD08MERSutrASOtoEK2QyHeR/CWwKBgQCz0Tw0kGE4eX6GDpd5dQvsS4pdhvC1anQXQswHAk1fVK2OKJvyvK3XbrV39CS4GfMU8e28Xhrmta5N5pW9eiTwTGxiMq/v2TjF2d15XzS22oSxQvbmwKBPyi0sKnmmHtPSKDNZzFu08z8jmLbWijucxXcj+clK3/2kk+YudllcqwKBgAGyG8/IbHsuSwzd80zjENUZHeWeSAehNGpKT1dGdImMA4ocj4IITmRBJ8qzDjdU6diGHR/l/r24fgT1j/sN0x+kyQhCkAeDH9WjoxQZ1SP3vlik1IqKDbb8ozHp0v3HzqyNkp9Ca1Ncmwlx6/KjKMOtj1zQ3c0V023zQf8Z0mKHAoGAKzjrGNNvmwyRHhYZ0OcSk5zgyHRsHZ9WAlVbB4BFKtoG0bnvKlyyCKui0Q+NV/dc17cDZxjTrIsVN8JqUd4vTNeB4eS/J472N5CTzN3WpPFHnfgzsumwXx2A2DHphcW5MrXZCge2L3ucvKizokQd6iQI7EvZV7cZl9q5JIfsGPsCgYANtQxJGrt+BclJAG5ANbyQmNowO5KMrKBaoSj37zJ9HtuPLhbv7b6yyKTg/RKUSJFPfvVj3w8zy66kLapMYQtlMfKQ0O70dVQ8ZqkTRkYoIlGFSS4xruqP1Rx9+OabEbvTxw7gxY6/bTiULXKv2gsU+DgWkDWhcYmgboLOy9jaGA==";
        String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv0HOyctXROVxRjc+uobemsoJ9W5N8VTIIszGaL3nEsg4CrfF5IjjNyYxnSih+CHFux5mS/8Z2rgr8oyYi4v0VZWQz/xRV40vyi2ZYgJ51bHadrKEfeU3PUsJ/3uO1TX2N+cyPR/Xbqu/lggQssqhs/LYACpf/2Iopb4lbD0CyMQNp6lPKYQhgJe3wmmtQ1u26Y1kHNG1qV+lSCm4zL067PjjZtdNG/+g7Vh4ZzwVqW6LLRn9f68jO8wGIQuvSWbbLZJ/fCr2QaqxU9arIHpsJYpk8gb19aHkvBSbOgj270mIk/35OPb8QMP9GbAGW0cKyrT1u9cHbg7jnzDjatJmKQIDAQAB";
        String serverUrl="https://openapi.alipaydev.com/gateway.do";

        //        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
//        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//        request.setBizModel(model);
//        model.setOutTradeNo(System.currentTimeMillis()+"");
//        model.setTotalAmount("1");
//        model.setSubject("Iphone6 16G");
//        AlipayTradePrecreateResponse response = alipayClient.execute(request);
//        System.out.print(response.getBody());
//        System.out.print("-----------------");
//        System.out.print(response.getQrCode());

        Map<Object, Object> resultMap = new HashMap<>(1);

        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, APP_ID,
                APP_PRIVATE_KEY, PayUtils.FORMAT, PayUtils.CHARSET, ALIPAY_PUBLIC_KEY, PayUtils.SIGN_TYPE);
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject("Iphone6 16G");
        model.setBody("Iphone6 16G");
        model.setOutTradeNo(System.currentTimeMillis()+"");

        // 一旦超时，该笔交易就会自动被关闭。当用户进入支付宝收银台页面（不包括登录页面），会触发即刻创建支付宝交易，此时开始计时。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
        model.setTimeoutExpress("30m");
        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        model.setTotalAmount("1");
        // 销售产品码，商家和支付宝签约的产品码，固定值
        model.setProductCode("QUICK_MSECURITY_PAY");

        request.setBizModel(model);
        request.setNotifyUrl("47.96.228.171:8013/v1/pay/ali/actions/notify");

        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            // 就是orderString 可以直接给客户端请求，无需再做处理。
            resultMap.put("sign", response.getBody());
        } catch (AlipayApiException e) {

        }

        System.out.print(""+resultMap);
    }
}
