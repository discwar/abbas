package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: 微信支付相关配置 </p>
 * <p>Description: 读取微信支付相关配置 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/29 16:48      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Getter
@Setter
public class WxPayConfig {

    /**
     * 微信开放平台审核通过的应用APPID
     */
    private String appId;
    /**
     * 微信支付分配的商户号
     */
    private String mchId;
    /**
     * 商户平台设置的API密钥
     */
    private String key;
    /**
     * 微信退款接口链接
     */
    private String refundUrl;

    /**
     * 微信证书存放路径
     */
     private String certPath;
}
