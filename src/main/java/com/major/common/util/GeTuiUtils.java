package com.major.common.util;

import com.major.config.GeTuiConfig;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

/**
 * <p>Title: 个推推送工具类 </p>
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

public class GeTuiUtils {

    /**
     * 立即推送
     * @param config
     * @return
     * @throws Exception
     */
    public static String pushToSingle(GeTuiConfig config) throws Exception {
        return pushToSingle(config, null, null);
    }

    /**
     * 对单个用户推送消息
     * 场景1：某用户发生了一笔交易，银行及时下发一条推送消息给该用户。
     * 场景2：用户定制了某本书的预订更新，当本书有更新时，需要向该用户及时下发一条更新提醒信息。
     * @param config
     * @begin 预约展示开始时间
     * @end 预约展示结束时间 两者时间起码要相差十分钟以上
     */
    public static String pushToSingle(GeTuiConfig config,String begin,String end) throws Exception {
        // 此处true为https域名， false为http， 默认为false。 Java语言推荐使用此方式
        IGtPush push = new IGtPush(config.getAppKey(), config.getMasterSecret(), true);
        NotificationTemplate template = notificationTemplate(config);
        // 设置定时展示时间 时间区间为10分钟-1小时
        if(begin!=null && end!=null ) {
            template.setDuration(begin,end);
        }
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(config.getAppId());
        target.setClientId(config.getClientId());
//        target.setAlias(Alias);
        IPushResult ret = null;
        String result=null;
        try {
            ret = push.pushMessageToSingle(message, target);
            System.out.println(ret.getResponse().toString());
            result="2000";
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
            result="3000";
        }
        if (ret != null) {
            return ret.getResponse().toString();
        }
        return result;
    }

    /**
     * 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用。
     * （激活后，打开应用的首页，如果只要求点击通知唤起应用，不要求到哪个指定页面就可以用此功能。
     * 应用场景：针对沉默用户，发送推送消息，点击消息栏的通知可直接激活启动应用，提升应用的转化率。
     * @param config
     * @return
     */
    public static NotificationTemplate notificationTemplate(GeTuiConfig config) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(config.getAppId());
        template.setAppkey(config.getAppKey());
        // 透传消息设置， 1为强制启动应用， 客户端接收到消息后就会立即启动应用； 2为等待应用启动
        template.setTransmissionType(config.getTransmissionType());
        template.setTransmissionContent(config.getTransmissionContent());
        //IOS设置消息
        APNPayload payload = new APNPayload();
        APNPayload.DictionaryAlertMsg msg = new APNPayload.DictionaryAlertMsg();
        msg.setTitle(config.getTitle());
        msg.setBody(config.getText());
        payload.setAlertMsg(msg);
        template.setAPNInfo(payload);

        // 设置定时展示时间 时间区间为10分钟-1小时
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(config.getTitle());
        style.setText(config.getText());
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃， 震动， 或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        return template;
    }

    /**
     * 在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页。
     * 应用场景：推送广促销活动，用户点击通知栏信息，直接打开到指定的促销活动页面，推送直接到达指定页面，免去了中间过程的用户流失。
     * @param config
     * @return
     */
    public static LinkTemplate linkTemplate(GeTuiConfig config) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(config.getAppId());
        template.setAppkey(config.getAppKey());
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(config.getTitle());
        style.setText(config.getText());
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        // 设置打开的网址地址
        template.setUrl(config.getUrl());
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }

//以下为测试数据
    static String appId = "D3PQ1tPHZK93HzdBqFu561";
    static String appKey = "BK8VtIUcgg8xFwGcS3grL8";
    static String masterSecret = "WeNfpOCIkx5WgTLbvjMfh3";
    static String host = "https://api.getui.com/apiex.htm";
  static final String CID = "5efc80c7ddaa021ff333d264ee3d70de";
    public static void main(String[] args) throws Exception {
        // https连接
        IGtPush push = new IGtPush(appKey, masterSecret, true);
        // 此处true为https域名，false为http，默认为false。Java语言推荐使用此方式
        // IGtPush push = new IGtPush(host, appkey, master);
        // host为域名，根据域名区分是http协议/https协议
        NotificationTemplate template = linkTemplateDemo();

        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        // 用户别名推送，cid和用户别名只能2者选其一
        // String alias = "个";
        // target.setAlias(alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }
    public static NotificationTemplate linkTemplateDemo()  {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置， 1为强制启动应用， 客户端接收到消息后就会立即启动应用； 2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent("你好啦1122");
        APNPayload payload = new APNPayload();

        APNPayload.DictionaryAlertMsg msg = new APNPayload.DictionaryAlertMsg();
        msg.setTitle("爱果官方回复啦");
        msg.setBody("意见收到了，我们会综合采纳用户意见，看在后期如何更好的优化。");
        payload.setAlertMsg(msg);

        template.setAPNInfo(payload);

        try {
            // 设置定时展示时间 时间区间为10分钟-1小时
          //  template.setDuration("2018-09-11 10:01:00","2018-09-11 10:59:00");

        }catch (Exception e){
            System.out.println("服务器响应异常");
        }
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("爱果官方回复啦");
        style.setText("意见收到了，我们会综合采纳用户意见，看在后期如何更好的优化。");
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃， 震动， 或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        return template;
    }

}
