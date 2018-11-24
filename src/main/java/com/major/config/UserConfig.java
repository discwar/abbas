package com.major.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/26 17:31
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "user")
@Getter
@Setter
public class UserConfig {

    /**
     * 用户重置密码
     */
    private String resetPassWord;

    private String maxRetryCount;


}
