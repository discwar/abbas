package com.major.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.crazycake.shiro.SerializeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/9/18 16:11      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class MyRedisCache<K, V> implements Cache<K, V> {

    private static final String REDIS_CACHE = "ag-cache:";
    private String cacheKey;
    private RedisTemplate<K, V> redisTemplate;
    private long expireTime;

    public MyRedisCache(String name, RedisTemplate client, long expireTime) {
        this.cacheKey = REDIS_CACHE;
        this.redisTemplate = client;
        this.expireTime = expireTime;
    }

    @Override
    public V get(K key) throws CacheException {
        return redisTemplate.boundValueOps(getCacheKey(key)).get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V old = get(key);
        redisTemplate.boundValueOps(getCacheKey(key)).set(value, expireTime, TimeUnit.MINUTES);
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        V old = get(key);
        redisTemplate.delete(getCacheKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }

}
