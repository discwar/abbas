package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title: 快递100相关配置，https://poll.kuaidi100.com </p>
 * <p>Description: 读取快递100相关配置 </p>
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
@ConfigurationProperties(prefix = "kuaidi100")
@Getter
@Setter
public class KuaiDi100Config {

    /**
     * 实时查询customer
     */
    private String customer;

    /**
     * 客户授权Key
     */
    private String key;

    /**
     * 订阅请求地址
     */
    private String host;

    /**
     * 回调接口的地址
     */
    private String callbackUrl;
    /**
     * 快递100实时快递查询接口请求地址
     */
    private String queryHost;
    // 忽略的Field
    /**
     * 查询参数
     * 格式：{"com":"yunda","num":"3325668832081","from":"","to":"","resultv2":0}
     */
    private String param;

}
