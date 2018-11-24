package com.major.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <p>Title: redisson配置 </p>
 * <p>Description: 单机模式 </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/8 9:26      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Configuration
public class RedissonConfig {

    @Value("${shiro.redis.host}")
    private String host;

    @Value("${shiro.redis.port}")
    private String port;

    @Value("${shiro.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonSingle() throws IOException {
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redis.yml"));
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
        return Redisson.create(config);
    }

}
