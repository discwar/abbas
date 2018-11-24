package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: 支付宝支付相关配置 </p>
 * <p>Description: 读取支付宝支付相关配置 </p>
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
@ConfigurationProperties(prefix = "ali.pay")
@Getter
@Setter
public class AliPayConfig {

    /**
     * 收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字（卖家支付宝用户号）
     */
    private String pid;

    /**
     * APPID即创建应用后生成
     */
    private String appId;
    /**
     * 开发者应用私钥，由开发者自己生成
     */
    private String appPrivateKey;
    /**
     * 支付宝公钥，由支付宝生成
     */
    private String aliPayPublicKey;
    /**
     * 设置未付款支付宝交易的超时时间
     */
    private String timeoutExpress;
    /**
     * 支付宝网关 支付/退款
     */
    private String serverUrl;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

}
