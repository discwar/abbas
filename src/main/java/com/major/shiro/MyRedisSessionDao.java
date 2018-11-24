package com.major.shiro;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/9/18 16:46      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Slf4j
@Setter
public class MyRedisSessionDao extends EnterpriseCacheSessionDAO {

    private int expireTime;

    private static String prefix = "ag-session:";

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建session，保存到数据库
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        log.debug("创建session:{}", session.getId());
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    /**
     * 获取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        log.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 删除session
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        log.debug("删除session:{}", session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }

}
