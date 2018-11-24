package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: 达达物流相关配置，http://newopen.imdada.cn/#/development/file/mustread?_k=jo760l </p>
 * <p>Description: 读取达达物流相关配置 </p>
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
@ConfigurationProperties(prefix = "dada")
@Getter
@Setter
public class DadaConfig {

    /**
     * 应用Key，对应开发者账号中的app_key，固定
     */
    private String appKey;

    /**
     * 应用密钥，对应开发者账号中的app_secret，固定
     */
    private String appSecret;

    /**
     * API版本，固定
     */
    private String v;

    /**
     * 商户编号（创建商户账号分配的编号），固定
     */
    private String sourceId;

    /**
     * 环境地址
     */
    private String host;


    /**
     * 回调地址
     */
    private String callbackUrl;


}
