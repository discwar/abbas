package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: 个推相关配置，http://docs.getui.com/ </p>
 * <p>Description: 读取个推相关配置 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/10 16:48      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Component
@ConfigurationProperties(prefix = "getui")
@Getter
@Setter
public class GeTuiConfig {

    /**
     * 用于鉴定身份是否合法
     */
    private String appKey;

    /**
     * 第三方客户端个推集成鉴权码，用于验证第三方合法性。在服务端推送鉴权时使用
     */
    private String masterSecret;

    /**
     * 推送 os 域名.host 可选填，如果 host 不填程序自动检测
     * 用户网络，选择最快的域名连接下发。 getui 服务 url 地
     * 址，可使用 https url 或者 http url (服务端 SDK 版本号
     * 3.4.0.0 及以上版本支持该功能)
     */
    private String host;

    /**
     * 设置客户端所属应用唯一ID
     */
    private String appId;

    // 忽略的Field
    /**
     * 客户端身份ID
     */
    private String clientId;

    /**
     * 通知栏标题
     */
    private String title;

    /**
     * 通知栏内容
     */
    private String text;

    /**
     * 设置打开的网址地址
     */
    private String url;

    /**
     * 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
     */
    private Integer transmissionType = 1;

    /**
     * 透传的内容
     */
    private String transmissionContent;

}
